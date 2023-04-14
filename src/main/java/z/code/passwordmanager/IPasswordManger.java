package z.code.passwordmanager;

public interface IPasswordManger {
    String generatePassword(int digits, int uppers, int lowers, int symbols);
}