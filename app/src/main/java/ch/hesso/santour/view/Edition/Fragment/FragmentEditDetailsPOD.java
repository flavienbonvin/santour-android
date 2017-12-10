package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ch.hesso.santour.R;
import ch.hesso.santour.view.Edition.Activity.TrackEditPODActivity;

public class FragmentEditDetailsPOD extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private View rootView;

    private EditText editNamePOD;
    private ImageView editImagePOD;
    private TextView editDescriptionPOD;
    private RatingBar editRatingPOD;

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
        editNamePOD  = (EditText) rootView.findViewById(R.id.edit_pod_textView_namePOD);
        editNamePOD.setText(TrackEditPODActivity.podDetails.getName());

        editImagePOD  = (ImageView) rootView.findViewById(R.id.edit_pod_imageView_imagePOD);
        //editImagePOD.setImageBitmap(TrackEditPODActivity.podDetails.);

        editDescriptionPOD  = (TextView) rootView.findViewById(R.id.edit_pod_textView_descriptionContent);
        //editDescriptionPOD.setText(TrackEditPODActivity.podDetails.);

        editRatingPOD  = (RatingBar) rootView.findViewById(R.id.edit_pod_ratingBar_rating);
        //editDescriptionPOD.setText(TrackEditPODActivity.podDetails.);

    }

    public void updateFiledsToDB(){
        TrackEditPODActivity.podDetails.setName(editNamePOD.getText().toString());
        //TrackEditPODActivity.podDetails.setImage(editImagePOD.);
        //TrackEditPODActivity.podDetails.setDescription(editDescriptionPOD.getText().toString()));
        //TrackEditPODActivity.podDetails.setRating(editRatingPOD.);
    }

}
