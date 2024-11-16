import java.util.Arrays;

public class TEST {
    public static void main(String[] args) {
        int []array = new int[5];
        int []arrayCopy = new int[array.length];
        int  i = 0;
        for(int element : array){
            arrayCopy[i] = element;
            i++;
        }
        System.out.println(Arrays.toString(arrayCopy));
    }
}
