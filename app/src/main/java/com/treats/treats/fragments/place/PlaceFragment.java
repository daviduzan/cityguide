package com.treats.treats.fragments.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.fragments.BaseFragment;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.Place;
import com.treats.treats.nodes.PlacesDataNode;

public class PlaceFragment extends BaseFragment {


    private static final String ARGS_KEY_PLACE_NAME = "args_key_place_name";
    private Place mPlace;

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
        String placeName = getArguments().getString(ARGS_KEY_PLACE_NAME);
        PlacesDataNode placesDataNode = (PlacesDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.PLACES);
        mPlace = placesDataNode.getPlace(placeName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        if (mPlace != null) {
            ((TextView) view.findViewById(R.id.tv_place_name)).setText(mPlace.mTitle);
            ((TextView) view.findViewById(R.id.tv_place_description)).setText(mPlace.mDescription);
        }
        return view;
    }

}
