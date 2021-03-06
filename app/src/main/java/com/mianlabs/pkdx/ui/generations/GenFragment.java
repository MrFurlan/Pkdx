/*
 * Copyright (C) 2016 Damián Adams
 */
package com.mianlabs.pkdx.ui.generations;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mianlabs.pkdx.R;
import com.mianlabs.pkdx.utilities.PokePicker;
import com.mianlabs.pkdx.utilities.PokeSharedPreferences;
import com.mianlabs.pkdx.utilities.typeface.TypefaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Displays the different generations available in the Pokemon
 * franchise to the user.
 */
public class GenFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GenFragment.class.getSimpleName();

    public static final String GEN_FRAG_KEY = "GenFragment";

    private AppCompatActivity mContext;
    private Typeface mCustomFont;


    @BindView(R.id.button_gen_i)
    Button mGenIButton;
    @BindView(R.id.button_gen_ii)
    Button mGenIIButton;
    @BindView(R.id.button_gen_iii)
    Button mGenIIIButton;
    @BindView(R.id.button_gen_iv)
    Button mGenIVButton;
    @BindView(R.id.button_gen_v)
    Button mGenVButton;
    @BindView(R.id.button_gen_vi)
    Button mGenVIButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gen, container, false);
        ButterKnife.bind(this, rootView);
        mContext = (AppCompatActivity) getActivity();
        mCustomFont = Typeface.createFromAsset(mContext.getAssets(), getString(R.string.font_path));
        setCustomTypefaceForViews(mCustomFont);

        mGenIButton.setOnClickListener(this);
        mGenIIButton.setOnClickListener(this);
        mGenIIIButton.setOnClickListener(this);
        mGenIVButton.setOnClickListener(this);
        mGenVButton.setOnClickListener(this);
        mGenVIButton.setOnClickListener(this);

        return rootView;
    }

    private void setCustomTypefaceForViews(Typeface customFont) {
        mGenIButton.setTypeface(customFont);
        mGenIIButton.setTypeface(customFont);
        mGenIIIButton.setTypeface(customFont);
        mGenIVButton.setTypeface(customFont);
        mGenVButton.setTypeface(customFont);
        mGenVIButton.setTypeface(customFont);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Sets the title of the Action Bar.
        int countOfCaughtPokemon = mContext
                .getSharedPreferences(PokeSharedPreferences.COUNT_CAUGHT_POKEMON_FILENAME, Context.MODE_PRIVATE)
                .getInt(PokeSharedPreferences.COUNT_CAUGHT_POKEMON_KEY, 0);
        TypefaceUtils.setActionBarTitle(mContext, getString(R.string.pokedex_name) +
                " " + countOfCaughtPokemon + "/" + PokePicker.NUM_OF_POKEMON);
    }

    /**
     * onClickListener for all of the generations buttons.
     */
    @Override
    public void onClick(View v) {
        PokePicker.Generations genVal;
        switch (v.getId()) {
            case R.id.button_gen_i:
                genVal = PokePicker.Generations.GEN_I;
                break;
            case R.id.button_gen_ii:
                genVal = PokePicker.Generations.GEN_II;
                break;
            case R.id.button_gen_iii:
                genVal = PokePicker.Generations.GEN_III;
                break;
            case R.id.button_gen_iv:
                genVal = PokePicker.Generations.GEN_IV;
                break;
            case R.id.button_gen_v:
                genVal = PokePicker.Generations.GEN_V;
                break;
            case R.id.button_gen_vi:
                genVal = PokePicker.Generations.GEN_VI;
                break;
            default:
                genVal = null;
                Log.e(TAG, "Error retrieving generation button ids.");
                break;
        }
        if (genVal != null) {
            PokeListFragment pokeListFragment = new PokeListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(GEN_FRAG_KEY, genVal);
            pokeListFragment.setArguments(bundle);

            if (!GenActivity.isTwoPane()) {
                getFragmentManager().beginTransaction().replace(R.id.generations_container, pokeListFragment)
                        .addToBackStack(null).commit();
            } else {
                getFragmentManager().beginTransaction().replace(R.id.poke_list_container, pokeListFragment).commit();
            }
        }
    }
}
