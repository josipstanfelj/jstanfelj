package compare;

import domain.Project;

import java.util.Comparator;

public class CompareProject implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        if(o1.getCurrentAmount() < o2.getCurrentAmount()){
            return 1;
        }
        else if(o1.getCurrentAmount() > o2.getCurrentAmount()){
            return -1;
        } else {
            return 0;
        }
    }
}
