package com.android.employeemanagement;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.employeemanagement.adapter.EmployeeListAdapter;
import com.android.employeemanagement.model.EmployeeRepository;
import com.android.employeemanagement.model.data.Employee;
import com.android.employeemanagement.model.data.SanctionedLeavesStatus;
import com.android.employeemanagement.view.EmployeeListFragment;
import com.android.employeemanagement.view_model.EmployeeListViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ankit on 14/4/18
 * 
 * Its the mainactivity where all callbacks were being there for furhter implementation
 */

public class MainActivity extends AppCompatActivity implements EmployeeListAdapter.onEmployeeClickListener {

    
    EmployeeListViewModel employeeListViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        employeeListViewModel =  EmployeeApplication.injectEmployeListViewModel();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EmployeeListFragment()).commit();
        }
    }

    Calendar myCalendar;
    List<SanctionedLeavesStatus> sanctionedLeavesStatuses;
    boolean showFlag=false;

    /**
     * Method being called when clicked on any item in employee list
     * @param employee Employee
     */
    @Override
    public void onEmployeeItemClick(final Employee employee) {

        LayoutInflater factory = LayoutInflater.from(this);
        View detailDialogView = factory.inflate(R.layout.fragment_employee_detail, null,false);

        final EmployeeRepository employeeRepository = employeeListViewModel.employeeRepository;
        
        detailDialogView.setBackgroundColor(Color.TRANSPARENT);
        final AlertDialog detailDialog = new AlertDialog.Builder(this).create();
        detailDialog.setView(detailDialogView);

        detailDialog.setCanceledOnTouchOutside(false);
        detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView employeeNameTextView = (TextView) detailDialogView.findViewById(R.id.employee_name_text_view);
        TextView employeeDesignationTextView = (TextView)detailDialogView.findViewById(R.id.employee_designation_text_view);
        ImageView profileImageView = (ImageView)detailDialogView.findViewById(R.id.thumbnailImage);

        if (employee!=null) {
            String employeNameField = employee.employee_name+" ("+employee.employee_age+", "+employee.employee_gender+" )";
            employeeNameTextView.setText(employeNameField);
            employeeDesignationTextView.setText(employee.employee_designation);
            Picasso.with(this).load(employee.profile_url).into(profileImageView);
        }
        
        final TextView noLeaveStatusDataText = (TextView)detailDialogView.findViewById(R.id.noLeaveStatusData);
        final ListView leaveInfoList = (ListView)detailDialogView.findViewById(R.id.list_leave_status);

        final EditText fromDateEditText = detailDialogView.findViewById(R.id.fromDateEditText);
        final EditText toDateEditText = detailDialogView.findViewById(R.id.toDateEditText);
        final EditText leaveReason = detailDialogView.findViewById(R.id.leave_reason_editText);
        
        TextView applyLeaveButton = detailDialogView.findViewById(R.id.apply_leave);
        Button saveButton = detailDialogView.findViewById(R.id.save_apply_leave);
        Button cancelButton = detailDialogView.findViewById(R.id.cancel_apply_leave);

        final LinearLayout leaveAddSection  = detailDialogView.findViewById(R.id.leaveAddSection);
        
        ImageView crossButton  = detailDialogView.findViewById(R.id.cancelDetailImage);
        
        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailDialog.dismiss();
            }
        });
        
        applyLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                    showFlag=!showFlag;
                    onShowHide(showFlag,leaveAddSection);
                
            }
        });
        
        toDateEditText.setKeyListener(null);
        fromDateEditText.setKeyListener(null);
        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 myCalendar= Calendar.getInstance();
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                       
                        
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        
                        String dateString = dayOfMonth+"/"+monthOfYear+"/"+year;
                        toDateEditText.setText(dateString);
                        
                    }

                };
                
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar= Calendar.getInstance();
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub



                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateString = dayOfMonth+"/"+monthOfYear+"/"+year;
                        fromDateEditText.setText(dateString);

                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fromDateEditText.getText().toString().isEmpty() && !toDateEditText.getText().toString().isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            
                            SanctionedLeavesStatus sanctionedLeavesStatus = new SanctionedLeavesStatus();
                            if (employee!=null) {
                                sanctionedLeavesStatus.employee_id = employee.employee_id;
                            }
                            sanctionedLeavesStatus.leave_from_date = fromDateEditText.getText().toString();
                            sanctionedLeavesStatus.leave_to_date = toDateEditText.getText().toString();
                            sanctionedLeavesStatus.leave_reason = leaveReason.getText().toString();
                            sanctionedLeavesStatus.leave_status  = "Pending";
                            
                            employeeRepository.storeLeaveSanctionedStatusInDb(sanctionedLeavesStatus);


                            if (employee!=null) {
                                sanctionedLeavesStatuses = employeeRepository.getLeaveSanctionedInfoByEmployee(employee.employee_id);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(sanctionedLeavesStatuses.size()==0)
                                        {
                                            leaveInfoList.setVisibility(View.INVISIBLE);
                                            noLeaveStatusDataText.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            leaveInfoList.setVisibility(View.VISIBLE);
                                            noLeaveStatusDataText.setVisibility(View.INVISIBLE);
                                        }

                                        leaveInfoList.setAdapter(new LeaveSanctionedAdapter(sanctionedLeavesStatuses));

                                    }
                                });
                            }
                        }
                    }).start();

                    showFlag=!showFlag;
                    onShowHide(showFlag,leaveAddSection);
                }
                if (fromDateEditText.getText().toString().isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    fromDateEditText.startAnimation(shake);
                }
                if (toDateEditText.getText().toString().isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    toDateEditText.startAnimation(shake);
                }
            }
        });
        
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDateEditText.setText("");
                toDateEditText.setText("");
                showFlag=!showFlag;
                onShowHide(showFlag,leaveAddSection);
            }
        });
        
        

      


        sanctionedLeavesStatuses = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (employee!=null) {
                    sanctionedLeavesStatuses = employeeRepository.getLeaveSanctionedInfoByEmployee(employee.employee_id);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(sanctionedLeavesStatuses.size()==0)
                            {
                                leaveInfoList.setVisibility(View.INVISIBLE);
                                noLeaveStatusDataText.setVisibility(View.VISIBLE);
                            }

                            leaveInfoList.setAdapter(new LeaveSanctionedAdapter(sanctionedLeavesStatuses));

                        }
                    });
                }
                
            }
        }).start();
       
        
      

        
        

        detailDialog.show();

       
    }

    /**
     * Show and Hide animation of leave add section
     * @param flag boolean
     * @param leaveAddSection view
     */
    public void onShowHide(boolean flag, final View leaveAddSection) {
        if (flag) {
            leaveAddSection.animate().setDuration(1000).alpha(1.0f).translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    leaveAddSection.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
            


        } else {




            leaveAddSection.animate().setDuration(1000).translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    // topLayout.setVisibility(View.GONE);
                    leaveAddSection.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();


        }
    }

    /**
     * Adapter for Showing leave information on same page
     */

    class LeaveSanctionedAdapter extends BaseAdapter {
        
        List<SanctionedLeavesStatus> sanctionedLeavesStatusList;
        LeaveSanctionedAdapter(List<SanctionedLeavesStatus> sanctionedLeavesStatuses)
        {
            this.sanctionedLeavesStatusList = sanctionedLeavesStatuses;
        }
        
        @Override
        public int getCount() {
            return sanctionedLeavesStatusList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            
            if(view==null)
            {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.leave_sanctioned_single_item,viewGroup,false);
            }
            
            TextView fromDateText  = view.findViewById(R.id.fromDateText);
            TextView toDateText = view.findViewById(R.id.toDateText);
            TextView leaveStatus = view.findViewById(R.id.leaveStatusText);
            ImageView leaveStatusImage  =  view.findViewById(R.id.leaveStatusImage);
            
            String date = "From:"+sanctionedLeavesStatusList.get(i).leave_from_date;
            fromDateText.setText(date);
            
            date = "To:"+sanctionedLeavesStatusList.get(i).leave_to_date;
            toDateText.setText(date);
            leaveStatus.setText(sanctionedLeavesStatusList.get(i).leave_status);
            if (sanctionedLeavesStatusList.get(i).leave_status.equalsIgnoreCase("Sanctioned")) {
                leaveStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.green_circle));
            } else if(sanctionedLeavesStatusList.get(i).leave_status.equalsIgnoreCase("Canceled")) {
                leaveStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.red_circle));
            }
            else if(sanctionedLeavesStatusList.get(i).leave_status.equalsIgnoreCase("Pending")){
                leaveStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.yellow_circle));
            }


            return view;
        }
    }
    
    
    private void changeLeaveStatus() {
        LayoutInflater factory = LayoutInflater.from(this);
        View changeLeaveDialog = factory.inflate(R.layout.change_leave_status_dialog, null,false);

        final EmployeeRepository employeeRepository = employeeListViewModel.employeeRepository;

        changeLeaveDialog.setBackgroundColor(Color.TRANSPARENT);
        final AlertDialog changeDialog = new AlertDialog.Builder(this).create();
        changeDialog.setView(changeLeaveDialog);
        
        
        
        
        
        changeDialog.show();
    }
    
    
    //Yet to implement the change leave status
    
   /* class ChangeLeaveAdapter extends BaseAdapter {
        
        List<Employee> employees;
        List<SanctionedLeavesStatus> sanctionedLeavesStatuses;
        ChangeLeaveAdapter(List<Employee> employees,List<SanctionedLeavesStatus> sanctionedLeavesStatuses) {
            this.employees = employees;
            this.sanctionedLeavesStatuses = sanctionedLeavesStatuses;
        }
        @Override
        public int getCount() {
            return sanctionedLeavesStatuses.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            
            if(view==null)
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.change_leave_single_item,viewGroup,false);
            
            
            TextView employeeNameText = view.findViewById(R.id.employee_name);
            TextView fromDateText = view.findViewById(R.id.fromDate);
            TextView toDateText = view.findViewById(R.id.toDate);
            AutoCompleteTextView statusText = view.findViewById(R.id.leave_status);
            
            String employee_name ="";
            for(Employee employee: employees)
            {
                if(employee.employee_id.equalsIgnoreCase(sanctionedLeavesStatuses.get(i).leave_status))
                {
                    employee_name = employee.employee_name;
                }
            }
            
            employeeNameText.setText(employee_name);
            fromDateText.setText(sanctionedLeavesStatuses.get(i).leave_from_date);
            toDateText.setText(sanctionedLeavesStatuses.get(i).leave_to_date);

            String[] array={"Pending","Canceled" ,"Sanctioned"};

            ArrayAdapter<String> adapter;

           


            adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, array);

            statusText.setAdapter(adapter);
            
            
            
            
            return view;
        }
    }*/


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.change_leave_status:
                changeLeaveStatus();
                break;
                
        }
        
        return true;
    }*/
}
