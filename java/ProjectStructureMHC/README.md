1. Run scripts from MAIN.sql file.
2. Create file with 16 characters long key inside. Use any name, any extension. Check <a href="./key.txt">key.txt</a> for example.
3. Update spring/app-config.xml (path/to/key) with correct path to your key file (like /home/user/key.txt). 
4. Encrypt your DB credentials with key from key.txt. Update datasource-encrypted.properties.
5. Update datasource.properties with correct jdbc URL.
6. Encrypt your keys with key from key.txt. salt and log_key also have to be 16 characters long. doc_key have to be 10 characters long. Save encrypted keys to DB.
    <code>insert into app_keys(salt,doc_key,log_key)
          values ('','','');</code>
7. Check profile to use and build project. Default profile is local.
8. Run the app.
