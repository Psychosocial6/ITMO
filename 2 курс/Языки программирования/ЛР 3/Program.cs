using System;
using System.IO.MemoryMappedFiles;
using System.Text;
using System.Threading;

public class BidirectionalPipe
{
    private const int MAX_PAIRS = 10;
    private const int BUFFER_SIZE = 1024;
    private const int MMF_SIZE = sizeof(int) + BUFFER_SIZE + sizeof(int) + BUFFER_SIZE;

    private enum Role {
        ProcA, 
        ProcB
    }

    private static volatile bool _isRunning = true;

    public static void Main(string[] args)
    {
        int pairId = -1;
        Role myRole = Role.ProcA;
        Mutex mutexA = null;
        Mutex mutexB = null;

        Console.WriteLine("Установка связи");

        for (int i = 0; i < MAX_PAIRS; i++)
        {
            bool createdNew;
            mutexA = new Mutex(true, $"BidiPipe_Mutex_A_Pair_{i}", out createdNew);
            if (createdNew)
            {
                myRole = Role.ProcA;
                pairId = i;
                Console.WriteLine("Процесс добавлен в пару. Ожидание второго процесса");

                using (EventWaitHandle partnerArrivedEvent = new EventWaitHandle(false, EventResetMode.AutoReset, $"BidiPipe_Event_B_Arrived_Pair_{i}"))
                {
                    partnerArrivedEvent.WaitOne();
                }
                break;
            }
            else
            {
                mutexA.Dispose();
                mutexB = new Mutex(true, $"BidiPipe_Mutex_B_Pair_{i}", out createdNew);
                if (createdNew)
                {
                    myRole = Role.ProcB;
                    pairId = i;
                    Console.WriteLine("Процесс добавлен в пару. Соединение установлено");

                    using (EventWaitHandle partnerArrivedEvent = EventWaitHandle.OpenExisting($"BidiPipe_Event_B_Arrived_Pair_{i}"))
                    {
                        partnerArrivedEvent.Set();
                    }
                    break;
                }
                mutexB.Dispose();
            }
        }

        if (pairId == -1)
        {
            Console.WriteLine("Превышение лимита установленных каналов");
            Console.ReadKey();
            return;
        }

        Console.WriteLine("Двусторонний канал между процессами установлен. Для выходна ввести exit");

        string mmfName = $"BidiPipe_MMF_Pair_{pairId}";
        string eventNameWrite = $"BidiPipe_Event_{myRole}_Writes_{pairId}";
        string eventNameRead = $"BidiPipe_Event_{(myRole == Role.ProcA ? Role.ProcB : Role.ProcA)}_Writes_{pairId}";
        string shutdownEventName = $"BidiPipe_Shutdown_Event_{pairId}";

        int writeOffset_Length = (myRole == Role.ProcA) ? 0 : sizeof(int) + BUFFER_SIZE;
        int writeOffset_Buffer = writeOffset_Length + sizeof(int);
        int readOffset_Length = (myRole == Role.ProcB) ? 0 : sizeof(int) + BUFFER_SIZE;
        int readOffset_Buffer = readOffset_Length + sizeof(int);

        try
        {
            using (MemoryMappedFile mmf = MemoryMappedFile.CreateOrOpen(mmfName, MMF_SIZE))
            using (MemoryMappedViewAccessor accessor = mmf.CreateViewAccessor())
            using (EventWaitHandle writeEvent = new EventWaitHandle(false, EventResetMode.AutoReset, eventNameWrite))
            using (EventWaitHandle readEvent = new EventWaitHandle(false, EventResetMode.AutoReset, eventNameRead))
            using (EventWaitHandle shutdownEvent = new EventWaitHandle(false, EventResetMode.ManualReset, shutdownEventName))
            {
                Thread readerThread = new Thread(() =>
                {
                    WaitHandle[] handles = {readEvent, shutdownEvent};
                    while (_isRunning)
                    {
                        int handleIndex = WaitHandle.WaitAny(handles);

                        if (handleIndex == 1 || !_isRunning)
                        {
                            break;
                        }

                        int messageLength = accessor.ReadInt32(readOffset_Length);
                        if (messageLength > 0)
                        {
                            byte[] buffer = new byte[messageLength];
                            accessor.ReadArray(readOffset_Buffer, buffer, 0, messageLength);
                            string message = Encoding.UTF8.GetString(buffer);
                            Console.WriteLine($"Input: {message}");
                        }
                    }
                });
                readerThread.Start();

                while (_isRunning)
                {
                    string input = Console.ReadLine();

                    if (string.Equals(input, "exit", StringComparison.OrdinalIgnoreCase) || input == null)
                    {
                        _isRunning = false;
                        shutdownEvent.Set();
                        break;
                    }

                    byte[] buffer = Encoding.UTF8.GetBytes(input);
                    accessor.Write(writeOffset_Length, buffer.Length);
                    accessor.WriteArray(writeOffset_Buffer, buffer, 0, buffer.Length);

                    writeEvent.Set();
                }

                readerThread.Join();
            }
        }
        finally
        {
            mutexA?.Dispose();
            mutexB?.Dispose();
            Console.WriteLine("Канал связи закрыт");
        }
    }
}