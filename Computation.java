import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by joren on 5/9/16.
 */
public abstract class Computation{

    private List<Object> steps = new LinkedList<>();

    protected ListIterator iterSteps = steps.listIterator();

    public Object nextStep(){
        return iterSteps.next();
    }

    public Object previousStep(){
        return iterSteps.previous();
    }

    public boolean isLastStep(){
        return iterSteps.hasNext();
    }

    public int getLength(){
        return steps.size();
    }
}
