package com.example.riotproject.frament;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.riotproject.LoadingApp;
import com.example.riotproject.R;
import com.example.riotproject.adapter.RecyleAdapter;

import com.example.riotproject.data.MathList;
import com.example.riotproject.data.MathReferenceDto;
import com.example.riotproject.data.Summoner;
import com.example.riotproject.request.AccountIdRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class FullSearchFragment extends Fragment implements View.OnClickListener{
    private EditText editText;
    private RecyclerView recyclerView;
    private RecyleAdapter adater;

    private int beginIndex,endIndex;

    private Summoner summoner;
    private MathList mathList;
    String nickName;


    ArrayList<MathReferenceDto>arrayList ;

    private boolean isLoding = false;

    final static String  API_KEY = "RGAPI-0e4da930-8bb8-40b8-aa89-b667168c1f32";
    public FullSearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        editText = view.findViewById(R.id.edit_search);
        view.findViewById(R.id.btn_test).setOnClickListener(this);


        recyclerView = view.findViewById(R.id.main_recycle);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosiotion = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                final int itemTotalCount = recyclerView.getAdapter().getItemCount();
                if (itemTotalCount == 0){
                    return;
                }
                if(lastVisibleItemPosiotion +1 == itemTotalCount && !isLoding){
                    LoadingApp.getInstance().progressON(getActivity(),null);
                    isLoding = true;
                    beginIndex = endIndex+1;
                    endIndex+= 21;

                   mathPageingLoad(lastVisibleItemPosiotion);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    @Override
    public void onClick(View view) {
        if(isLoding) return;
        LoadingApp.getInstance().progressON(getActivity(),null);
        isLoding = true;


        if(editText.getText().toString().length() == 0){
            Toast.makeText(view.getContext(),"닉을 입력 하세용",Toast.LENGTH_SHORT).show();
            hideLoding();
            return;
        }
        methLoad();
    }

    private void uiErrorMethod(final String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                hideLoding();
                return;

            }
        });
    }

    private void methLoad(){

        final ContentValues param2 = new ContentValues();

        param2.put("api_key",API_KEY);
        param2.put("queue",String.valueOf(450));
        param2.put("beginIndex",0);
        param2.put("endIndex",20);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nickName = editText.getText().toString();
                    ContentValues param = new ContentValues();
                    param.put("api_key",API_KEY);
                    final String result = new AccountIdRequest(param,getActivity()).execute("lol/summoner/v4/summoners/by-name/",nickName).get();

                    if(result.equals("애러")){
                        uiErrorMethod("애러~");
                        return;
                    }else if(result.equals("404")){
                        uiErrorMethod("존재하지않는 닉네임 입니다;;");
                        return;
                    }

                    Summoner feSummoner = summonerParer(result);

                    final String result2 = new AccountIdRequest(param2,getActivity()).execute("lol/match/v4/matchlists/by-account/",feSummoner.getAccountId()).get();

                    // 존재하는 유저 + 칼바람 전적도 있음
                    if(!result2.equals("404") &&!result2.equals("애러") ){
                        summoner = feSummoner;
                        if(arrayList != null){
                            arrayList.clear();
                        }
                        arrayList= new ArrayList<>();
                        beginIndex = 0;
                        endIndex = 20;
                        mathListParser(result2,summoner.getName());

                        adater = new RecyleAdapter(getActivity(),mathList.getMathes());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DividerItemDecoration dividerItemDecoration =
                                        new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.addItemDecoration(dividerItemDecoration);
                                recyclerView.setAdapter(adater);
                                hideLoding();
                            }
                        });
                    }else{
                        uiErrorMethod("칼바람러가 아닙니다");
                    }
                }catch (ExecutionException e){
                    e.printStackTrace();
                    hideLoding();
                    return;
                }catch (InterruptedException e){
                    e.printStackTrace();
                    hideLoding();
                    return;
                }
            }
        }).start();
    }


    private void mathPageingLoad(final int itemTotalCount){
        final ContentValues param2 = new ContentValues();
        param2.put("api_key",API_KEY);
        param2.put("queue",String.valueOf(450));
        param2.put("beginIndex",beginIndex);
        param2.put("endIndex",endIndex);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result2 = new AccountIdRequest(param2,getActivity()).execute("lol/match/v4/matchlists/by-account/",summoner.getAccountId()).get();

                    if(!result2.equals("404") && !result2.equals("애러")){
                        final MathList list =mathListParser(result2,summoner.getName());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoding();
                                if(list == null){
                                    beginIndex -= 21;
                                    endIndex-= 21;
                                    Toast.makeText(getContext(),"list 가 null임;;",Toast.LENGTH_SHORT).show();
                                    return;
                                }else{
                                    adater.notifyItemInserted(itemTotalCount + 1);
                                    Log.d("test",""+adater.getItemCount());
                                }
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beginIndex -= 21;
                                endIndex-= 21;
                                hideLoding();
                                uiErrorMethod("애러임;;");
                                return;
                            }
                        });
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                    hideLoding();
                    return;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    hideLoding();
                    return;
                }
            }
        }).start();
    }

    private void hideLoding(){
        isLoding = false;
        LoadingApp.getInstance().setIsLoadingFlag(false);
        LoadingApp.getInstance().progressOFF();
    }

    private Summoner summonerParer(String resultJson) {
        Summoner summoner = new Summoner();
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            summoner.setProfileIconId(jsonObject.getInt("profileIconId"));
            summoner.setAccountId(jsonObject.getString("accountId"));
            summoner.setName(jsonObject.getString("name"));
            summoner.setPuuid(jsonObject.getString("puuid"));
            summoner.setSummonerLevel(jsonObject.getLong("summonerLevel"));
            summoner.setId(jsonObject.getString("id"));
            summoner.setRevisionDate(jsonObject.getLong("revisionDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return summoner;
    }

    private MathList mathListParser(String json,String edText) {
        try {

            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("matches");
            if(jsonArray.length() == 0){
                return null;
            }

            mathList = new MathList();
            mathList.setStartIndex(jsonObject.getInt("startIndex"));
            mathList.setEndIndex(jsonObject.getInt("endIndex"));
            mathList.setTotalGames(jsonObject.getInt("totalGames"));
             //ArrayList<MathReferenceDto>arrayList = new ArrayList<>()
            for (int i = 0; i < jsonArray.length(); i++) {
                MathReferenceDto mathReferenceDto = new MathReferenceDto();
                JSONObject matchesObject = jsonArray.getJSONObject(i);
                mathReferenceDto.setChampion(matchesObject.getInt("champion"));
                mathReferenceDto.setPlatformId(matchesObject.getString("platformId"));
                mathReferenceDto.setGameId(matchesObject.getLong("gameId"));
                mathReferenceDto.setQueue(matchesObject.getInt("queue"));
                mathReferenceDto.setSeason(matchesObject.getInt("season"));
                mathReferenceDto.setTimestamp(matchesObject.getLong("timestamp"));
                mathReferenceDto.setRole(matchesObject.getString("role"));
                mathReferenceDto.setLane(matchesObject.getString("lane"));
                mathReferenceDto.setGameId(matchesObject.getLong("gameId"));


                // mathDeatilList Request

                ContentValues param3 = new ContentValues();
                param3.put("api_key", API_KEY);

                String result3 = new AccountIdRequest(param3,getActivity()).execute("lol/match/v4/matches/" + mathReferenceDto.getGameId()).get();

                JSONObject detailObj = new JSONObject(result3);
                JSONArray partPantJsonArray = detailObj.getJSONArray("participantIdentities");
                int participantId = 0;
                for(int j =0;j<partPantJsonArray.length();j++){
                    JSONObject player = partPantJsonArray.getJSONObject(j);
                    if(player.getJSONObject("player").getString("summonerName").equals(edText)){
                        participantId = player.getInt("participantId");
                        break;
                    }
                }
                JSONArray participantsArray = detailObj.getJSONArray("participants");

                Log.d("testtest", "participantId ==> " + participantId);
                JSONObject participantObj = participantsArray.getJSONObject(participantId-1);


                //스펠
                mathReferenceDto.setSpell1(participantObj.getInt("spell1Id"));
                mathReferenceDto.setSpell2(participantObj.getInt("spell2Id"));

                JSONObject participantStatsObj = participantObj.getJSONObject("stats");
                //승 && 패 & KDA
                mathReferenceDto.setWin(participantStatsObj.getBoolean("win"));
                mathReferenceDto.setKills(participantStatsObj.getInt("kills"));
                mathReferenceDto.setAssists(participantStatsObj.getInt("assists"));
                mathReferenceDto.setDeaths(participantStatsObj.getInt("deaths"));

                //아이템
                mathReferenceDto.setItem0(participantStatsObj.getInt("item0"));
                mathReferenceDto.setItem1(participantStatsObj.getInt("item1"));
                mathReferenceDto.setItem2(participantStatsObj.getInt("item2"));
                mathReferenceDto.setItem3(participantStatsObj.getInt("item3"));
                mathReferenceDto.setItem4(participantStatsObj.getInt("item4"));
                mathReferenceDto.setItem5(participantStatsObj.getInt("item5"));

                //룬특성 스타일
                mathReferenceDto.setMainRuenStyle(participantStatsObj.getInt("perkPrimaryStyle"));
                mathReferenceDto.setSubRuneStyle(participantStatsObj.getInt("perkSubStyle"));

                mathReferenceDto.setRune0(participantStatsObj.getInt("perk0"));
                mathReferenceDto.setRune1(participantStatsObj.getInt("perk1"));
                mathReferenceDto.setRune2(participantStatsObj.getInt("perk2"));
                mathReferenceDto.setRune3(participantStatsObj.getInt("perk3"));
                mathReferenceDto.setRune4(participantStatsObj.getInt("perk4"));
                mathReferenceDto.setRune5(participantStatsObj.getInt("perk5"));

                arrayList.add(mathReferenceDto);
            }

            mathList.setMathes(arrayList);
            Log.d("arrayList","arrayList ==>" + mathList.getMathes().size());
            //LoadingApp.getInstance().setIsLoadingFlag(false);
           // LoadingApp.getInstance().progressOFF();
            return mathList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }

    }
}
