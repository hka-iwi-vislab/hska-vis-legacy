package services.user.model;

public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }

    public String getFirstName(){
        return firstName;
    }
    
    public void setFirsName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }

    public Role getRole(){
        return role;
    }
    
    public void setRole(Role role){
        this.role = role;
    }
}
