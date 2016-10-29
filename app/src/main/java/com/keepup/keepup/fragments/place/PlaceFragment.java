package com.keepup.keepup.fragments.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keepup.keepup.App;
import com.keepup.keepup.R;
import com.keepup.keepup.fragments.user_lists.add_to_list_dialog.AddToListDialogFragment;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.fragments.BaseFragment;
import com.keepup.keepup.infra.nodes.NodesProvider;
import com.keepup.keepup.models.Place;
import com.keepup.keepup.nodes.PlacesDataNode;

public class PlaceFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARGS_KEY_PLACE_NAME = "args_key_place_name";
    private Place mPlace;
    private String mPlaceName;

    public PlaceFragment() {
    }

    public static PlaceFragment newInstance(String placeName) {
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_PLACE_NAME, placeName);
        PlaceFragment placeFragment = new PlaceFragment();
        placeFragment.setArguments(args);
        return placeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlaceName = getArguments().getString(ARGS_KEY_PLACE_NAME);
        PlacesDataNode placesDataNode = (PlacesDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.PLACES);
        mPlace = placesDataNode.getPlace(mPlaceName);
        if (mPlace == null) {
            getFragmentManager().popBackStack();
            App.toast("Sorry! no such place " + mPlaceName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        if (mPlace != null) {
            ((TextView) view.findViewById(R.id.tv_place_name)).setText(mPlace.mTitle);
            ((TextView) view.findViewById(R.id.tv_place_description)).setText(mPlace.mDescription);
            view.findViewById(R.id.btn_add).setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:

                AddToListDialogFragment addToListDialogFragment = AddToListDialogFragment.newInstance(mPlaceName);

                addToListDialogFragment.show(getChildFragmentManager(), mPlaceName);


//                UserDataNode userDataNode = (UserDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.USER);
//                userDataNode.addPlaceToUserList("test", mPlaceName);

                break;
        }
    }
}
