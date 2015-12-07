package myPackage.hellokevin;



import java.util.ArrayList;

public class Structlist<E> extends ArrayList<E> implements List<E>  {
   private int Max_list = 161;
    private E[] item;
    private  int numitems;

    @SuppressWarnings("unchecked")
    public void initializelist() {
        item = (E[]) new Object[Max_list];
        numitems = 0;
    }

    public void initializelist(int size) {
        Max_list=size;
        item = (E[]) new Object[Max_list];
        numitems = 0;
    }


    public void add(int index, E item)  throws ListisFullException, IndexOutOfBounds{

        if (index>0 && index <= numitems + 1){

            if (numitems==Max_list){
                throw new ListisFullException("List is ALready FUll!");
            }
            else
            {
                int temp= numitems;
                while (temp>=index)
                {
                    this.item[temp]=this.item[temp-1];
                    temp--;
                }
                this.item[index-1]=item;
                numitems++;
            }

        }

        else{
            throw new IndexOutOfBounds ("Index is Out of Bounds! Please Enter a value within the max range!");
        }
    }


    public void removeItem(int index)  throws  IndexOutOfBounds{
        if(index>0 && index <numitems+1){
            for(int i=index;i>numitems;i++){
                this.item[i-1]=this.item[i];
            }
            numitems--;

        }
        else{
            throw new IndexOutOfBounds ("Index is Out of Bounds! Please Enter a value within the max range!");
        }

    }


    public E getitem(int index) throws  IndexOutOfBounds {
        if(index>0 && index<numitems+1){
            return (item[index-1]);
        }
        else {
            throw new IndexOutOfBounds ("Index is Out of Bounds! Please Enter a value within the max range!");
        }
    }


    public int listsize() {
        return numitems;
    }


    public boolean isempty() {
        return (numitems>0);
    }



    public void removeall() {
        numitems=0;

    }

    public int getMax(){
        return Max_list;
    }
}