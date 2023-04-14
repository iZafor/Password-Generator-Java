package z.code.passwordmanager;

public interface IPasswordManger {
    public String generatePassword(int digits, int uppers, int lowers, int symbols);
}