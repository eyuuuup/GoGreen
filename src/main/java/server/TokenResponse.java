package server;

public class TokenResponse {
    private String token;
    private boolean bool;

    public TokenResponse(String token, boolean bool) {
        this.token = token;
        this.bool = bool;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public static class User {
        private static String name;
        private static String password;

        /**
         * This method creates and instantiates a new User.
         * @param newName the Name
         * @param password the password
         */
        public User(String newName, String password) {
            this.name = newName;
            this.password = password;
        }

        /**
         * Getter for name.
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Changes name into newName.
         * @param newName the new name
         */
        public void changeName(String newName) {
            name = newName;
        }

        /**
         * Getter for password.
         * @return password
         */
        public String getPassword() {
            return password;
        }

        /**
         * Changes name into new password.
         * @param newPassword the new name
         */
        public void changePassword(String newPassword) {
            this.password = newPassword;
        }
    }
}
