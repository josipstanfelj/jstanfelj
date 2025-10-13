package threads;


import domain.SuspendedUsers;


public class GetAllSuspendedUsersThread extends DatabaseThread implements Runnable{
    public SuspendedUsers suspendedUsers;

    public GetAllSuspendedUsersThread(){}

    @Override
    public void run() {
        suspendedUsers = super.getAllSuspendedUsers();
    }

    public SuspendedUsers getSuspendedUsers() {
        return suspendedUsers;
    }
}
