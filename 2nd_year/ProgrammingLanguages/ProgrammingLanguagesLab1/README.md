### Сборка из командной строки


1.  **Клонируйте репозиторий:**
    ```bash
    git clone [URL]
    cd ProgrammingLanguagesLab1 
    ```

2.  **Создайте директорию для сборки и сконфигурируйте проект:**
    ```bash
    cmake -B build
    ```

3.  **Соберите проект:**
    ```bash
    cmake --build build
    ```

4.  **Запустите приложение:**

    *   **На Windows (в PowerShell или CMD):**
        ```powershell
        .\build\ProgrammingLanguagesLab1.exe
        ```
    *   **На Linux или macOS:**
        ```bash
        ./build/ProgrammingLanguagesLab1
        ```
