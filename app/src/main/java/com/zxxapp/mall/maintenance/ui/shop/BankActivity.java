package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivityBankBinding;
import com.zxxapp.mall.maintenance.databinding.ItemBankBinding;

import java.util.LinkedList;
import java.util.List;

public class BankActivity extends AppCompatActivity {
    private ActivityBankBinding binding;
    private List<Bank> bankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank);

        binding.bankList.setLayoutManager(new LinearLayoutManager(this));

        initBankList();

        BankListAdapter adapter = new BankListAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v) {
                int position = binding.bankList.getChildAdapterPosition(v);
                Bank bank = bankList.get(position);
                if(bank!=null){
                    Intent intent = new Intent();
                    intent.putExtra("title", bank.getTitle());
                    intent.putExtra("code", bank.getCode());
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });
        binding.bankList.setAdapter(adapter);
    }

    private void initBankList() {
        bankList = new LinkedList<>();

        Bank bank = new Bank();
        bank.setTitle("中国工商银行");
        bank.setCode("ICBC_DEBIT");
        bank.setLogo(R.drawable.ic_bank_icbc);
        bankList.add(bank);

        bank = new Bank();
        bank.setTitle("中国农业银行");
        bank.setCode("ABC_DEBIT");
        bank.setLogo(R.drawable.ic_bank_abc);
        bankList.add(bank);

        bank = new Bank();
        bank.setTitle("中国建设银行");
        bank.setCode("CCB_DEBIT");
        bank.setLogo(R.drawable.ic_bank_ccb);
        bankList.add(bank);

        bank = new Bank();
        bank.setTitle("中国银行");
        bank.setCode("BOC_DEBIT");
        bank.setLogo(R.drawable.ic_bank_boc);
        bankList.add(bank);
    }

    class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder>{
        private OnItemClickListener onItemClickListener;
        public void setOnItemClickListener(OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final ItemBankBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_bank, parent, false);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null) {
                        onItemClickListener.onClick(binding.getRoot());
                    }
                }
            });

            return new BankViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
            Bank bank = bankList.get(position);
            holder.binding.logoImage.setImageResource(bank.getLogo());
            holder.binding.titleText.setText(bank.getTitle());
        }

        @Override
        public int getItemCount() {
            return bankList.size();
        }

        class BankViewHolder extends RecyclerView.ViewHolder{
            ItemBankBinding binding;

            public BankViewHolder(ItemBankBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    class Bank{
        private String title;
        private String code;
        private int logo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }
    }

    interface OnItemClickListener{
        void onClick(View v);
    }
}
