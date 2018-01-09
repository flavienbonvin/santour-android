package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ch.hesso.santour.R;
import ch.hesso.santour.business.PictureFirebaseManagement;
import ch.hesso.santour.business.PictureManagement;
import ch.hesso.santour.view.Edition.Activity.TrackEditPOIActivity;

public class FragmentEditDetailsPOI extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private View rootView;

    private EditText editNamePOI;
    private ImageView editImagePOI;
    private TextView editDescriptionPOI;


    public FragmentEditDetailsPOI() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_edit_details_poi, container, false);

        initFields();

        return  rootView;
    }

    private void initFields(){
        editNamePOI  = rootView.findViewById(R.id.edit_track_textView_namePOI);
        editNamePOI.setText(TrackEditPOIActivity.poiDetails.getName());

        editImagePOI  = rootView.findViewById(R.id.edit_poi_imageView_imagePOI);
        PictureFirebaseManagement.downloadFile(TrackEditPOIActivity.poiDetails.getPicture());
        editImagePOI.setImageBitmap(BitmapFactory.decodeFile(PictureManagement.localStoragePath+TrackEditPOIActivity.poiDetails.getPicture()));

        editDescriptionPOI  = rootView.findViewById(R.id.edit_poi_textView_descriptionContent);
        editDescriptionPOI.setText(TrackEditPOIActivity.poiDetails.getDescription());
    }


}
