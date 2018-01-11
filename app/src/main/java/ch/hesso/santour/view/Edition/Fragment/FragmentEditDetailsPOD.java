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
import ch.hesso.santour.view.Edition.Activity.TrackEditPODActivity;

public class FragmentEditDetailsPOD extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private View rootView;

    public FragmentEditDetailsPOD() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.edition_fragment_edit_details_pod, container, false);

        initFields();

        return rootView;
    }

    private void initFields(){
        EditText editNamePOD = rootView.findViewById(R.id.edit_pod_textView_namePOD);
        editNamePOD.setText(TrackEditPODActivity.podDetails.getName());

        ImageView editImagePOD = rootView.findViewById(R.id.edit_pod_imageView_imagePOD);
        PictureFirebaseManagement.downloadFile(TrackEditPODActivity.podDetails.getPicture());
        editImagePOD.setImageBitmap(BitmapFactory.decodeFile(PictureManagement.localStoragePath+TrackEditPODActivity.podDetails.getPicture()));


        TextView editDescriptionPOD = rootView.findViewById(R.id.edit_pod_textView_descriptionContent);
        editDescriptionPOD.setText(TrackEditPODActivity.podDetails.getDescription());
    }

}
