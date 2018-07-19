1. Replace '<base href="/">' with '<base href="">'
2. Run 'npm install'
2.1 npm install -g @angular/cli
3. Build angular solution, run 'ng build --env=qa' (without the parameter 'env', the build will be with dev enviroment).
4. Copy the files inside the 'dist' folder into tomcat path

