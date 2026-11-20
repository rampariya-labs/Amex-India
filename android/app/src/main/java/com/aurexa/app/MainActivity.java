package com.aurexa.app;

import android.os.Bundle;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    private LinearLayout contentHost;
    private BottomNavigationView navigation;
    private boolean darkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentHost = new LinearLayout(this);
        contentHost.setOrientation(LinearLayout.VERTICAL);
        android.widget.FrameLayout frame = findViewById(R.id.content_frame);
        frame.addView(contentHost, new android.widget.FrameLayout.LayoutParams(-1, -1));
        navigation = findViewById(R.id.bottom_navigation);
        darkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        navigation.setOnItemSelectedListener(item -> { showScreen(item.getItemId()); return true; });
        navigation.setSelectedItemId(R.id.nav_home);
    }

    private void showScreen(int id) {
        contentHost.removeAllViews();
        View screen = id == R.id.nav_cards ? cardsScreen() : id == R.id.nav_activity ? activityScreen()
                : id == R.id.nav_analytics ? insightsScreen() : id == R.id.nav_profile ? profileScreen() : homeScreen();
        contentHost.addView(screen, new LinearLayout.LayoutParams(-1, -1));
    }

    private ScrollView page() { ScrollView s = new ScrollView(this); s.setFillViewport(true); return s; }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(1); l.setPadding(dp(20), dp(22), dp(20), dp(28)); return l; }
    private LinearLayout padded() { LinearLayout l = new LinearLayout(this); l.setOrientation(1); l.setPadding(dp(18), dp(18), dp(18), dp(18)); return l; }
    private int dp(int n) { return (int)(n * getResources().getDisplayMetrics().density + .5f); }
    private int color(int attr) { android.util.TypedValue v = new android.util.TypedValue(); getTheme().resolveAttribute(attr, v, true); return v.data; }
    private TextView txt(String value, float size, boolean bold) { TextView t = new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(color(com.google.android.material.R.attr.colorOnSurface)); if (bold) t.setTypeface(null, 1); return t; }
    private void gap(LinearLayout l, int h) { l.addView(new View(this), new LinearLayout.LayoutParams(1, dp(h))); }
    private MaterialCardView card(View child, int radius) { MaterialCardView c = new MaterialCardView(this); c.setRadius(dp(radius)); c.setCardElevation(dp(1)); c.setCardBackgroundColor(color(com.google.android.material.R.attr.colorSurfaceContainer)); c.addView(child); LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.bottomMargin = dp(14); c.setLayoutParams(p); return c; }
    private TextView heading(String value) { TextView t = txt(value, 18, true); LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.topMargin = dp(12); p.bottomMargin = dp(12); t.setLayoutParams(p); return t; }

    private View homeScreen() {
        ScrollView s = page(); LinearLayout root = column(); s.addView(root);
        root.addView(txt("AUREXA", 14, true)); TextView label = txt("FICTIONAL FINANCE SIMULATOR", 10, true); label.setTextColor(0xFFC9A45C); label.setLetterSpacing(.12f); root.addView(label);
        gap(root, 26); root.addView(txt("Total demo balance", 14, false)); gap(root, 5); root.addView(txt("₹8,45,250.00", 32, true));
        TextView disclaimer = txt("Illustrative balance · Not real money", 11, false); disclaimer.setAlpha(.65f); root.addView(disclaimer); gap(root, 22);
        LinearLayout stats = new LinearLayout(this); stats.setOrientation(0); stats.addView(stat("RECEIVED", "+₹1,25,000", true), new LinearLayout.LayoutParams(0, -2, 1));
        View space = new View(this); stats.addView(space, new LinearLayout.LayoutParams(dp(10), 1)); stats.addView(stat("SPENT", "−₹32,500", false), new LinearLayout.LayoutParams(0, -2, 1)); root.addView(stats);
        root.addView(heading("Your virtual card")); root.addView(metalCard()); root.addView(heading("Recent activity"));
        root.addView(transaction("Demo deposit", "Today · Simulator", "+₹25,000", true)); root.addView(transaction("Shopping", "Yesterday · Simulator", "−₹4,500", false)); root.addView(transaction("Money received", "18 Nov · Simulator", "+₹12,000", true)); return s;
    }
    private View stat(String label, String amount, boolean positive) { LinearLayout l = padded(); l.addView(txt(label, 10, true)); gap(l, 8); TextView v = txt(amount, 17, true); if (positive) v.setTextColor(0xFF4CAF82); l.addView(v); return card(l, 18); }
    private View metalCard() {
        LinearLayout body = new LinearLayout(this); body.setOrientation(1); body.setPadding(dp(20), dp(20), dp(20), dp(20)); body.setBackgroundColor(0xFF242321);
        TextView brand = txt("AUREXA  /  PRIVATE", 12, true); brand.setTextColor(0xFFE2C98F); body.addView(brand); gap(body, 24);
        TextView number = txt("••••  ••••  ••••  2048", 19, true); number.setTextColor(0xFFF7F1E5); body.addView(number); gap(body, 18);
        LinearLayout bottom = new LinearLayout(this); bottom.setGravity(Gravity.CENTER_VERTICAL); TextView member = txt("DEMO MEMBER", 10, true); member.setTextColor(0xFFD4C7AB); bottom.addView(member, new LinearLayout.LayoutParams(0, -2, 1)); TextView type = txt("METAL", 11, true); type.setTextColor(0xFFE2C98F); bottom.addView(type); body.addView(bottom);
        MaterialCardView c = new MaterialCardView(this); c.setRadius(dp(20)); c.setCardElevation(dp(3)); c.addView(body); LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, dp(178)); p.bottomMargin = dp(18); c.setLayoutParams(p); return c;
    }
    private View transaction(String title, String sub, String amount, boolean incoming) { LinearLayout row = new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL); row.setPadding(0, dp(12), 0, dp(12)); LinearLayout info = new LinearLayout(this); info.setOrientation(1); info.addView(txt(title, 14, true)); TextView small = txt(sub, 11, false); small.setAlpha(.65f); info.addView(small); row.addView(info, new LinearLayout.LayoutParams(0, -2, 1)); TextView value = txt(amount, 14, true); if (incoming) value.setTextColor(0xFF4CAF82); row.addView(value); return row; }
    private View cardsScreen() { ScrollView s = page(); LinearLayout r = column(); s.addView(r); r.addView(txt("Your cards", 27, true)); gap(r, 8); r.addView(txt("Manage fictional payment cards and demo limits.", 13, false)); gap(r, 20); r.addView(metalCard()); r.addView(info("Primary demo card", "Status: Active in simulator\nDemo limit: ₹2,00,000\nPayments: Simulated only")); r.addView(button("Create demo card", v -> toast("Card creation is planned for a later step."))); return s; }
    private View activityScreen() { ScrollView s = page(); LinearLayout r = column(); s.addView(r); r.addView(txt("Activity", 27, true)); gap(r, 8); r.addView(txt("Your fictional transaction history.", 13, false)); gap(r, 18); r.addView(transaction("Demo deposit", "20 Nov 2026 · Simulator", "+₹25,000", true)); r.addView(transaction("Shopping", "19 Nov 2026 · Simulator", "−₹4,500", false)); r.addView(transaction("Money received", "18 Nov 2026 · Simulator", "+₹12,000", true)); r.addView(transaction("Cafe payment", "17 Nov 2026 · Simulator", "−₹850", false)); r.addView(info("Demo data", "These entries are samples. They are not bank transactions or real payments.")); return s; }
    private View insightsScreen() { ScrollView s = page(); LinearLayout r = column(); s.addView(r); r.addView(txt("Insights", 27, true)); gap(r, 8); r.addView(txt("A simple view of your sample finances.", 13, false)); gap(r, 20); r.addView(info("Monthly overview", "Received\n₹1,25,000\n\nSpent\n₹32,500\n\nNet change\n+₹92,500")); r.addView(info("Spending categories", "Shopping       45%\nFood                 25%\nTravel               20%\nOther                10%")); return s; }
    private View profileScreen() {
        ScrollView s = page(); LinearLayout r = column(); s.addView(r); r.addView(txt("Profile & controls", 27, true)); gap(r, 6); r.addView(txt("Manage your simulator preferences.", 13, false)); gap(r, 18);
        r.addView(info("Demo profile", "Member name: Demo Member\nAccount type: Fictional personal\nCurrency: INR · Indian Rupee\nEnvironment: Offline simulator")); r.addView(heading("Appearance"));
        LinearLayout theme = padded(); theme.addView(txt("Dark appearance", 15, true)); TextView detail = txt("Switch between dark and light themes.", 12, false); detail.setAlpha(.7f); theme.addView(detail);
        SwitchMaterial toggle = new SwitchMaterial(this); toggle.setText("Use dark theme"); toggle.setChecked(darkMode); toggle.setOnCheckedChangeListener((b, checked) -> { darkMode = checked; AppCompatDelegate.setDefaultNightMode(checked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO); }); theme.addView(toggle); r.addView(card(theme, 18));
        r.addView(heading("Demo financial controls")); r.addView(info("Coming in later steps", "Edit demo net worth and balance\nAdd deposits and withdrawals\nRecord received and sent money\nCreate and edit expenses\nChange displayed network/provider settings"));
        r.addView(button("Reset demo preview", v -> toast("Reset will be added with local database support.")));
        TextView disclaimer = txt("AUREXA INDIA IS A FICTIONAL SIMULATOR. NO REAL BANKING, CARD NETWORK OR PAYMENT SERVICE IS CONNECTED.", 10, true); disclaimer.setAlpha(.7f); disclaimer.setGravity(Gravity.CENTER); disclaimer.setPadding(dp(8), dp(18), dp(8), dp(10)); r.addView(disclaimer); return s;
    }
    private View info(String title, String body) { LinearLayout l = padded(); l.addView(txt(title, 15, true)); gap(l, 9); TextView d = txt(body, 13, false); d.setLineSpacing(dp(3), 1f); l.addView(d); return card(l, 18); }
    private View button(String label, View.OnClickListener listener) { MaterialButton b = new MaterialButton(this); b.setText(label); b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFC9A45C)); b.setTextColor(0xFF171717); b.setOnClickListener(listener); return b; }
    private void toast(String message) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }
}
