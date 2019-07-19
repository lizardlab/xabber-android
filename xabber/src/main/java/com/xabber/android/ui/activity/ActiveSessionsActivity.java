package com.xabber.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.xabber.android.R;
import com.xabber.android.data.Application;
import com.xabber.android.data.account.AccountItem;
import com.xabber.android.data.account.AccountManager;
import com.xabber.android.data.entity.AccountJid;
import com.xabber.android.data.intent.AccountIntentBuilder;
import com.xabber.android.ui.color.BarPainter;

public class ActiveSessionsActivity extends ManagedActivity {

    private Toolbar toolbar;
    private BarPainter barPainter;

    private AccountItem accountItem;

    public static Intent createIntent(Context context, AccountJid account) {
        return new AccountIntentBuilder(context, ActiveSessionsActivity.class).setAccount(account).build();
    }

    private static AccountJid getAccount(Intent intent) {
        return AccountIntentBuilder.getAccount(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_sessions);

        final Intent intent = getIntent();
        AccountJid account = getAccount(intent);
        if (account == null) {
            finish();
            return;
        }

        accountItem = AccountManager.getInstance().getAccount(account);
        if (accountItem == null) {
            Application.getInstance().onError(R.string.NO_SUCH_ACCOUNT);
            finish();
            return;
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_default);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.account_active_sessions);
        barPainter = new BarPainter(this, toolbar);
        barPainter.updateWithAccountName(account);
    }
}
