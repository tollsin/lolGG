package com.example.riotproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.riotproject.R;
import com.example.riotproject.data.Champions;
import com.example.riotproject.data.MathReferenceDto;
import com.example.riotproject.data.Runes;
import com.example.riotproject.data.Spell;
import com.example.riotproject.data.Spells;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class RecyleAdapter extends RecyclerView.Adapter<RecyleAdapter.Holder> {

    private Context context;
    private ArrayList<MathReferenceDto> items;

    public RecyleAdapter(Context context, ArrayList<MathReferenceDto> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.entirely_item,null);
        Holder holder = new Holder(v,this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {


        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date date = new Date(items.get(position).getTimestamp());
        Date currentTime = new Date(System.currentTimeMillis());
        long diff = currentTime.getTime() - date.getTime();
        long diff2 = diff/3600000;

        if(diff2 > 24){
         long day = diff2/24;
            holder.tvTime.setText(day+ "일전" );
        }else
            holder.tvTime.setText(diff2+ "시간전");


        holder.tvDate.setText(dateFormat.format(date));


        MathReferenceDto item = items.get(position);

        holder.tvKill.setText(String.valueOf(item.getKills()));
        holder.tvAsi.setText(String.valueOf(item.getAssists()));
        holder.tvDeath.setText(String.valueOf(item.getDeaths()));
        if(item.isWin()){
            holder.tvReulst.setText("승");
            holder.riResutl.setBackgroundColor(Color.rgb(102,153,255));
        }else{
            holder.tvReulst.setText("패");
            holder.riResutl.setBackgroundColor(Color.rgb(255,000,000));
        }
            // item 이미지 로드
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem0()+".png").error(R.drawable.frame_loading_01).into(holder.item0);
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem1()+".png").error(R.drawable.frame_loading_01).into(holder.item1);
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem2()+".png").error(R.drawable.frame_loading_01).into(holder.item2);
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem3()+".png").error(R.drawable.frame_loading_01).into(holder.item3);
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem4()+".png").error(R.drawable.frame_loading_01).into(holder.item4);
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/item/" + item.getItem5()+".png").error(R.drawable.frame_loading_01).into(holder.item5);

        // spell 이미지 로드
        if(Spells.getInstance().getSpellMap().get(String.valueOf(item.getSpell1())) != null)
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/spell/"+ Spells.getInstance().getSpellMap().get(String.valueOf(item.getSpell1())).getImagePath()).error(R.drawable.frame_loading_01).into(holder.spell1);

        if(Spells.getInstance().getSpellMap().get(String.valueOf(item.getSpell2())) != null)
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/spell/"+ Spells.getInstance().getSpellMap().get(String.valueOf(item.getSpell2())).getImagePath()).error(R.drawable.frame_loading_01).into(holder.spell2);

        // rune 이미지 로드
        if(Runes.getInstance().getCoreRunesMap().get(String.valueOf(item.getMainRuenStyle())) != null && Runes.getInstance().getCoreRunesMap().get(String.valueOf(item.getMainRuenStyle())).getRuneMap().get(String.valueOf(item.getRune0())) != null)
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/img/"+Runes.getInstance().getCoreRunesMap().get(String.valueOf(item.getMainRuenStyle())).getRuneMap().get(String.valueOf(item.getRune0())).getIconImgPath()).into(holder.rune1);
        if(Runes.getInstance().getCoreRunesMap().get(String.valueOf(item.getSubRuneStyle())) != null)
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/img/"+Runes.getInstance().getCoreRunesMap().get(String.valueOf(item.getSubRuneStyle())).getIconImgPath()).into(holder.rune2);

        //champion 이미지 로드
        if(Champions.getInstance().getChampionMap().get(String.valueOf(items.get(position).getChampion())) != null){
            String champion= Champions.getInstance().getChampionMap().get(String.valueOf(items.get(position).getChampion())).getImagePath().getFull();
            Glide.with(context).load("http://ddragon.leagueoflegends.com/cdn/9.13.1/img/champion/" + champion).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.champImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.champImg.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }





    public class Holder extends RecyclerView.ViewHolder {
        private ImageView champImg;
        private TextView tvTime,tvDate,tvReulst,tvKill,tvAsi,tvDeath;
        private ImageView item0,item1,item2,item3,item4,item5;
        private ImageView spell1,spell2;
        private ImageView rune1,rune2;
        private RelativeLayout riResutl;



        private RecyleAdapter adapter;
        public Holder(@NonNull View v,RecyleAdapter adapter) {
            super(v);
            this.adapter = adapter;

            tvReulst = v.findViewById(R.id.tv_game_result);
            tvKill =v.findViewById(R.id.tv_kill);
            tvAsi = v.findViewById(R.id.tv_asi);
            tvDeath = v.findViewById(R.id.tv_death);

            tvTime =v.findViewById(R.id.tv_time);
            tvDate = v.findViewById(R.id.tv_game_date);

            item0 = v.findViewById(R.id.img_item0);
            item1 = v.findViewById(R.id.img_item1);
            item2 = v.findViewById(R.id.img_item2);
            item3 = v.findViewById(R.id.img_item3);
            item4 = v.findViewById(R.id.img_item4);
            item5 = v.findViewById(R.id.img_item5);

            spell1 = v.findViewById(R.id.spel_one);
            spell2 = v.findViewById(R.id.spel_two);

            rune1 = v.findViewById(R.id.rune_one);
            rune2 = v.findViewById(R.id.rune_two);
            champImg = v.findViewById(R.id.champ_img);

            riResutl= v.findViewById(R.id.ri_result);

        }

    }
}






