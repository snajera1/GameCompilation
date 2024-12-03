public interface Game {
    default public boolean checkWin(){
        return false;
    }
    public void showMessage(String title, String message);
    public void returnMain();
}
