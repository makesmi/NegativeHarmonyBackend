
package harmony.domain;

public class FacebookTokenInformation {
    
    private Content data;

    public Content getData() {
        return data;
    }

    public void setData(Content data) {
        this.data = data;
    }
    
    public static class Content{
        private boolean is_valid;
        private String user_id;

        public boolean isIs_valid() {
            return is_valid;
        }

        public void setIs_valid(boolean is_valid) {
            this.is_valid = is_valid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
