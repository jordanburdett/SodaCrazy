package com.group4.sodacrazy;

import android.content.Intent;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuildActivity extends AppCompatActivity {

    // declare array adapters that will change the value of the
    // spinners in the activity
    private ArrayAdapter<CharSequence>bev_size_adapter;

    /**
     * Initial price for beverage depending on size
     */
    private double base_price;
    private double extra_price;
    private TextView priceDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);

        base_price = 0;
        extra_price = 0;

        priceDisplay = (TextView) findViewById(R.id.price_display);
        updatePrice();

        /**
         * The Spinner responsible for showing the types of beverages that can be selected
         */
        final Spinner beverage_type = (Spinner) findViewById(R.id.beverage_spinner);

        /**
         * The spinner that will show the appropriate sizes of beverage depending on the selected
         * beverage type in {@link beverage_type}.
         */
        final Spinner bev_size = (Spinner) findViewById(R.id.bev_size_spinner);

        /**
         * Sets the adapter with appropriate array of strings depending on the spinner selection
         * and applies it to the next spinner so that it may show the appropriate sizes for each
         * beverage
         *
         * @param   AdapterView.OnItemSelectedListener() handles the actual adaptation
         */
        beverage_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                // switch of options that will determine the next spinner's options
                switch (pos) {
                    case  1: // Italian Ice
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.italian_ice_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  2: // The Craze
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.craze_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  3: // Shake It Up
                    case  4: // Crazy Ice Drink
                    case  5: // Float
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.shake_crazy_float_sizes, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  6: // Custard Shake
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.custard_shake_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  7: // Custard
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.custard_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  8: // Italian Soda
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.italian_drink_size, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    case  9: // Coffee
                    case 10: // Hot Chocolate
                    case 11: // Steamer
                    case 12: // Chi Tea
                        bev_size_adapter = ArrayAdapter.createFromResource(parentView.getContext(), R.array.hot_drink_sizes, android.R.layout.simple_spinner_dropdown_item);
                        bev_size.setAdapter(bev_size_adapter);
                        bev_size.setVisibility(View.VISIBLE);
                        break;
                    default:
                        bev_size.setVisibility(View.INVISIBLE);
                        base_price = 0;
                        break;
                }
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                base_price = 0;
                bev_size.setVisibility(View.INVISIBLE);
            }
        });

        /**
         * Adds appropriate price for the selected size and beverage type
         *
         * @param   AdapterView.OnItemSelectedListener()
         */
        bev_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String option = bev_size.getSelectedItem().toString();
                String bev = beverage_type.getSelectedItem().toString();
                if (bev != "Select a beverage...") {
                    switch (option) {
                        case "Small":
                            base_price = 2.10;
                            break;
                        case "Regular":
                            switch (bev) {
                                case "Italian Ice":
                                    base_price = 2.60;
                                    break;
                                case "The Craze":
                                    base_price = 3.85;
                                    break;
                                case "Custard Shake":
                                    base_price = 4.50;
                                    break;
                                default:
                                    base_price = 0.0;
                                    break;
                            }
                            break;
                        case "Large":
                            switch (bev) {
                                case "Italian Ice":
                                    base_price = 3.35;
                                    break;
                                case "The Craze":
                                    base_price = 4.35;
                                    break;
                                case "Custard Shake":
                                    base_price = 5.00;
                                    break;
                                default:
                                    base_price = 0.0;
                                    break;
                            }
                            break;
                        case "Small (custard on top only)":
                            base_price = 3.15;
                            break;
                        case "Regular - 16oz":
                            switch (bev) {
                                case "Shake It Up":
                                    base_price = 4.35;
                                    break;
                                case "Crazy Ice Drink":
                                    base_price = 4.35;
                                    break;
                                case "Float":
                                    base_price = 3.25;
                                    break;
                                default:
                                    base_price = 0.0;
                                    break;
                            }
                            break;
                        case "Large - 240z":
                            switch (bev) {
                                case "Shake It Up":
                                    base_price = 4.85;
                                    break;
                                case "Crazy Ice Drink":
                                    base_price = 4.85;
                                    break;
                                case "Float":
                                    base_price = 3.75;
                                    break;
                                default:
                                    base_price = 0.0;
                                    break;
                            }
                            break;
                        case "Cone":
                            base_price = 2.25;
                            break;
                        case "Waffle Cone":
                            base_price = 3.25;
                            break;
                        case "Small Cup":
                            base_price = 2.75;
                            break;
                        case "Regular Cup":
                            base_price = 3.25;
                            break;
                        case "Large Cup":
                            base_price = 4.00;
                            break;
                        default:
                            base_price = 0.0;
                            break;

                    }
                }
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                base_price = 0.0;
            }
        });
    }

    /*
     * These functions are for the menu bar
     * */
    public void toPunch(View view) {
        Intent intent = new Intent(this, PunchCardActivity.class);
        startActivity(intent);
    }

    public void toBuild(View view) {
        //do nothing
    }

    public void toMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void toHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Formats a string that will display the price of the built beverage in the priceDisplay
     * view.
     */
    private void updatePrice() {
        String price = "$" + String.format("%.2f", base_price + extra_price);
        priceDisplay.setText(price);
    }
}
