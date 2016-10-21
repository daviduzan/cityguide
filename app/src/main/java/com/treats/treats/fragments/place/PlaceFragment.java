package com.treats.treats.fragments.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.fragments.user_lists.add_to_list_dialog.AddToListDialogFragment;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.fragments.BaseFragment;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.Place;
import com.treats.treats.nodes.PlacesDataNode;

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
