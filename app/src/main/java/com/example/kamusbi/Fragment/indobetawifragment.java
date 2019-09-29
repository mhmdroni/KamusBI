package com.example.kamusbi.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.example.kamusbi.Adapter.KamusBetawiIndoAdapter;
import com.example.kamusbi.Algoritma.BoyerMoore;
import com.example.kamusbi.Algoritma.Horspool;
import com.example.kamusbi.DB.DBHelper;
import com.example.kamusbi.Model.Kamus;
import com.example.kamusbi.R;
import com.example.kamusbi.Utils.CommonUtil;

public class indobetawifragment extends Fragment {
    DBHelper dbHelper;
    EditText etHasil;
    EditText etAddText;
    EditText etWaktu;
    Spinner etAlgoritma;
    int failure[];
    RecyclerView recyclerViewKamus;
    KamusBetawiIndoAdapter adapter;

    LinearLayout layHasilTranslate;
    LinearLayout layHint;

    List<Kamus> kamusList;
    List<Kamus> kamusListTemp = new ArrayList<>();

    int pilih_algo = 1;

    String pattern = "";
    String hasil = "";

    public indobetawifragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_kamus, container, false);
        etAddText = (EditText) v.findViewById(R.id.add_teks);
        etHasil = (EditText) v.findViewById(R.id.ken_hasil_terjemah);
        etWaktu = (EditText) v.findViewById(R.id.ken_waktu);
        etAlgoritma = (Spinner) v.findViewById(R.id.ket_algoritma);
        Button btnTranslate = (Button) v.findViewById(R.id.btn_terjemah);
        final Button btnOcr = (Button) v.findViewById(R.id.btn_ocr);
        recyclerViewKamus = (RecyclerView) v.findViewById(R.id.rvkamuS);

        layHasilTranslate = (LinearLayout) v.findViewById(R.id.lay_hasil_terjemah);
        layHint = (LinearLayout) v.findViewById(R.id.lay_hint);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewKamus.setLayoutManager(llm);

        kamusList = dbHelper.getAllKamus();
        kamusListTemp = new ArrayList<>();

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pilih_algo == 1) {
                    prosesBoyerMoore();
                } else {
                    prosesHorspool();
                }
            }
        });

        btnOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goestoPicture();
            }
        });
        final String[] algoritma = getActivity().getResources().getStringArray(R.array.algoritma);
        etAlgoritma.setText(algoritma[0]);

        etAlgoritma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.dialogArray(getActivity(), algoritma, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etAlgoritma.setText(algoritma[i]);

                        if (i == 0) {
                            pilih_algo = 1;
                        } else if (i == 1) {
                            pilih_algo = 2;
                        }
                    }
                });
            }
        });
        return v;
    }

    private void prosesHorspool() {
        pattern = etAddText.getText().toString().toLowerCase();
        kamusListTemp.clear();

        Horspool horspool = new Horspool();

        for (int i = 0; i < kamusList.size(); i++) {
            int starttime = (int) System.nanoTime();
            int searchIndex = Horspool.horspool(kamusList.get(i).getIndo(), pattern);
            int endtime = (int) System.nanoTime() - starttime;

            float second = endtime / 1000F;
            etWaktu.setText(second + " Milliseconds");

            Log.d("HOORSPOOL", ": INDEX " + searchIndex);

            if (searchIndex != -1) {
                kamusListTemp.add(kamusList.get(i));
            }
        }
        prosesView();
    }

    private void prosesBoyerMoore() {
        pattern = etAddText.getText().toString().toLowerCase();
        kamusListTemp.clear();

        BoyerMoore bm = new BoyerMoore();

        for (int i = 0; i < kamusList.size(); i++) {
            int starttime = (int) System.nanoTime();
            int searchIndex = bm.findPattern(kamusList.get(i).getIndo(), pattern);
            int endtime = (int) System.nanoTime() - starttime;

            float second = endtime / 1000F;
            etWaktu.setText(second + " Milliseconds");

            Log.d("BOYERMOORE", ": Ada" + searchIndex);

            if (searchIndex != -1) {
                kamusListTemp.add(kamusList.get(i));
            }
        }

        prosesView();
    }

    void prosesView() {
        boolean matchString = false;
        adapter = new KamusBetawiIndoAdapter(kamusListTemp, getActivity());
        Log.d("KAMUS HASIL", "prosesView: " + kamusListTemp.size());
        if (kamusListTemp.size() > 0) {
            for (int i = 0; i < kamusListTemp.size(); i++) {
                if (pattern.equals(kamusListTemp.get(i).getIndo())) {
                    etHasil.setText(kamusListTemp.get(i).getBetawi());
                    etHasil.setTextColor(Color.BLUE);
                    matchString = true;
                    Log.d("True", kamusListTemp.get(i).getIndo());
                } else if (pattern.contains(kamusListTemp.get(i).getIndo())) {
                    Log.d("Mungkin Maksud Anda", kamusListTemp.get(i).getBetawi());
                    matchString = false;
                }
            }
            if (matchString == true) {
                layHasilTranslate.setVisibility(View.VISIBLE);
                layHint.setVisibility(View.GONE);
            } else if (matchString == false) {
                recyclerViewKamus.setAdapter(adapter);
                layHint.setVisibility(View.VISIBLE);
                layHasilTranslate.setVisibility(View.GONE);
            }
        } else if (kamusListTemp.size() == 0) {
            layHasilTranslate.setVisibility(View.VISIBLE);
            etHasil.setText("Data Tidak di Temukan".toUpperCase());
            etHasil.setTextColor(Color.RED);
            layHint.setVisibility(View.GONE);
        }

        if (hasil == null){
            etHasil.setTextColor(Color.RED);
            etHasil.setText("Data Tidak Ditemukan");
        }else {
            etHasil.setTextColor(Color.BLUE);
            etHasil.setText(getIndonesia(pattern));
        }
    }

    private String getIndonesia(String addText) {
        Kamus kamus = dbHelper.getIndo(addText);
        return kamus.getIndo();
    }

    private void goestoPicture() {
    }

}
