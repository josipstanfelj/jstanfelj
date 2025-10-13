package domain;

import java.util.HashMap;
import java.util.Map;

public class SuspendedUsers {
    public Map<Integer,Integer> suspendedUsersMap;
    public Map<Integer,String> reasonsMap;

    public SuspendedUsers(){
        suspendedUsersMap = new HashMap<>();
        reasonsMap = new HashMap<>();
    }


    public Map<Integer, Integer> getSuspendedUsersMap() {
        return suspendedUsersMap;
    }

    public void setSuspendedUsersMap(Map<Integer, Integer> suspendedUsersMap) {
        this.suspendedUsersMap = suspendedUsersMap;
    }

    public Map<Integer, String> getReasons() {
        return reasonsMap;
    }

    public void setReasons(Map<Integer, String> reasons) {
        this.reasonsMap = reasons;
    }
}
