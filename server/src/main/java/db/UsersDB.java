package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.config.Config;
import shared.model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class UsersDB {

    private static File gsonFolder;
    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public static void setConfig(Config config)
    {
        gsonFolder = new File(config.getProperty(String.class, "UsersDirectory").orElse(""));
    }

    public static User get(String username) {
        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(username)) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    User user = gson.fromJson(fileReader, User.class);
                    fileReader.close();

                    return user;
                }
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load user by username");
        }

        return null;
    }

    public static LinkedList<User> getAll()
    {
        LinkedList<User> users = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);
                users.add(user);
                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Collections.sort(users);
        return users;
    }

    public static void add(User user) {
        try {
            File file = new File(gsonFolder.getPath() + "/" + user.getUsername());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(user , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }

    public static void update(User user) {
        try {
            File file = new File(gsonFolder.getPath() + "/" + user.getUsername());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(user , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }
}
