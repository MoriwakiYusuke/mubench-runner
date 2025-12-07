package wordpressa._3.mocks.android.os;

public interface Parcelable {
    int describeContents();
    void writeToParcel(Object dest, int flags);
    
    interface Creator<T> {
        T createFromParcel(Object source);
        T[] newArray(int size);
    }
}
