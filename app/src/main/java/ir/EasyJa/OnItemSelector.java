package ir.EasyJa;


//************************ CLICK LISTENER FOR RECYCLERVIEW ITEMS

public interface OnItemSelector {

    public void onItemSelect(int position, String string);

    public void onItemClick(int position);

    public void onItemDelete(int position);

    public void onCardClick(int position);

    public void onEdit(int position, String no);
}
