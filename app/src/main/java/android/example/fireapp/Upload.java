package android.example.fireapp;

/**
 * Upload objects to store the data and retrieve it easily. This object has two main properties to use it.
 *@date 24.05.2020
 *@author Group 3C
 */
public class Upload {

    //our uploaded image have 2 main properties : specific url and the name user typed in.
    private String mName;
    private String mImageURL;

    public Upload(){ //default constructor
    }

    //constructor

    public Upload(String mName, String mImageURL){
        if(mName.trim().equals("")){
            mName = "no name";
        }
        this.mName = mName;
        this.mImageURL = mImageURL;
    }

    //getters and setters

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
