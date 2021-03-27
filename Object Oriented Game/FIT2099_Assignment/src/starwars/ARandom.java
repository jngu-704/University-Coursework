package starwars;

import java.util.AbstractList;
import java.util.Random;

public class ARandom {
    
    private static Random rand = new Random(); 

    public static <T> T itemFrom(AbstractList<T> list)
    {
	return list.get(rand.nextInt(list.size()));
    }
}
