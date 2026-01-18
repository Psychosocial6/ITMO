# Менеджер плейлиста

Консольное приложение на C# для управления музыкальным плейлистом. Программа позволяет добавлять, удалять, просматривать и искать композиции по автору/названию. Для сохранения используется СУБД PostgreSQL и Entity Framework Core.

## Установка и запуск

### 1. Клонирование репозитория
```bash
git clone https://gitlab.se.ifmo.ru/Psychosocial/programminglanguageslab2.git
cd programminglanguageslab2
```

### 2. Настройка базы данных

1.  **Проброс порта через SSH**

    Для подключения к удаленному серверу `helios` необходимо создать SSH-туннель. Выполните в вашем локальном терминале (эту команду нужно будет выполнять каждый раз перед запуском приложения, и терминал должен оставаться открытым):
    ```bash
    ssh -L 5432:localhost:5432 sXXXXXX@helios.cs.ifmo.ru -p2222
    ```

2.  **Создание таблицы**

    Подключитесь к базе данных `studs`:
    ```bash
    psql -h pg -d studs
    ```
    После подключения выполните следующий SQL-запрос для создания таблицы:
    ```sql
    CREATE TABLE compositions (
        id SERIAL PRIMARY KEY,
        number INTEGER NOT NULL UNIQUE,
        author TEXT NOT NULL,
        title TEXT NOT NULL,
        duration TEXT NOT NULL
    );
    ```

### 3. Настройка проекта

Откройте файл проекта `config.txt` и установите свои имя пользователя и пароль для подключения к БД.

### 4. Сборка и запуск

```bash
# Восстановление зависимостей
dotnet restore

# Запуск приложения
dotnet run
```
