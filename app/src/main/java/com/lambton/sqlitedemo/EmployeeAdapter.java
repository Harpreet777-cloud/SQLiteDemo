package com.lambton.sqlitedemo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//
//import com.s20.databasedemo_android.room.Employee;
//import com.s20.databasedemo_android.room.EmployeeRoomDb;
//import com.s20.databasedemo_android.util.DatabaseHelper;

import com.lambton.sqlitedemo.model.Employee;

import java.net.CookieHandler;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

   public class EmployeeAdapter  extends ArrayAdapter {

       private static final String TAG = "EmployeeAdapter";
       private final int layoutRes;
       //  private final List<Employee> employeelist;
       private final Context context;
       private final List<Employee> employeelist;
       List<Employee> employeeList;


       public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employeeList, SQLiteDatabase sqLiteDatabase) {
           super(context, resource, employeeList);
           this.employeelist = employeeList;
           this.context = context;
           this.layoutRes = resource;
           // employeeRoomDb = EmployeeRoomDb.getInstance(context);
       }

       @NonNull
       @Override
       public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

           LayoutInflater inflater = LayoutInflater.from(context);

           int layoutRes = 0;
           View v = inflater.inflate(layoutRes, null);
           TextView nameTextView = v.findViewById(R.id.row_name);
           TextView salaryTextView = v.findViewById(R.id.row_salary);
           TextView departmentTextView = v.findViewById(R.id.row_department);
           TextView dateTextView = v.findViewById(R.id.row_joining_date);


           employeeList = null;
           Employee employee = (Employee) employeeList.get(position);
           nameTextView.setText(employee.getName());
           salaryTextView.setText(String.valueOf(employee.getSalary()));
           departmentTextView.setText(employee.getDepartment());

           dateTextView.setText(employee.getJoiningDate());


           v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   updateEmployee(employee);
               }

               private void updateEmployee(final Employee employee) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                   LayoutInflater layoutInflater = LayoutInflater.from(context);
                   View view = layoutInflater.inflate(R.layout.dialog_update_employee, null);
                   builder.setView(view);
                   final AlertDialog alertDialog = builder.create();
                   alertDialog.show();

                   final EditText etName = view.findViewById(R.id.et_name);
                   final EditText etSalary = view.findViewById(R.id.et_salary);
                   final Spinner spinnerDept = view.findViewById(R.id.spinner_dept);

                   String[] deptArray = context.getResources().getStringArray(R.array.departments);
                   int position = Arrays.asList(deptArray).indexOf(employee.getDepartment());

                   etName.setText(employee.getName());
                   etSalary.setText(String.valueOf(employee.getSalary()));
                   spinnerDept.setSelection(position);

                   view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           String name = etName.getText().toString().trim();
                           String salary = etSalary.getText().toString().trim();
                           String department = spinnerDept.getSelectedItem().toString();

                           if (name.isEmpty()) {
                               etName.setError("name field cannot be empty");
                               etName.requestFocus();
                               return;
                           }

                           if (salary.isEmpty()) {
                               etSalary.setError("salary cannot be empty");
                               etSalary.requestFocus();
                               return;
                           }

                           String sql = "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?";
                           SQLiteDatabase sqLiteDatabase = null;
                           sqLiteDatabase.execSQL(sql, new String[]{name, department, salary, String.valueOf(employee.getId())});

                        /*if (sqLiteDatabase.updateEmployee(employee.getId(), name, department, Double.parseDouble(salary)))
                            loadEmployees();*/

                           // Room
                           //   employeeRoomDb.employeeDao().updateEmployee(employee.getId(),
                           //   name, department, Double.parseDouble(salary));
                           // loadEmployees();
                           alertDialog.dismiss();
                       }

//                     private void loadEmployees() {
//                       }
                   });
               }
           });

           v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   deleteEmployee(employee);
               }

               private void deleteEmployee(final Employee employee) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                   builder.setTitle("Are you sure?");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                        /*String sql = "DELETE FROM employee WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});*/
                        /*if (sqLiteDatabase.deleteEmployee(employee.getId()))
                            loadEmployees();*/
                           // Room

                           //  employeeRoomDb.employeeDao().deleteEmployee(employee.getId());
                           loadEmployees();
                       }
                   });
                   builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Toast.makeText(context, "The employee (" + employee.getName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                       }
                   });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();
               }
           });
           Log.d(TAG, "getView: " + getCount());
           return v;
       }

       //
       @Override
       public int getCount() {
           HashMap<Object, Object> employeeList = null;
           return employeeList.size();
       }

       //
       private void loadEmployees() {
           String sql = "SELECT * FROM employee";

           SQLiteDatabase sqLiteDatabase = null;
           Cursor cursor = sqLiteDatabase.rawQuery(sql, null);


           LinkedList<Object> employeeList = null;
           employeeList.clear();
           if (cursor.moveToFirst()) {
               do {
                   // create an employee instance
                   employeeList.add(new Employee(
                           cursor.getInt(0),
                           cursor.getString(1),
                           cursor.getString(2),
                           cursor.getString(3),
                           cursor.getDouble(4)
                   ));
               } while (cursor.moveToNext());
               cursor.close();
           }


//          // employeeList = employeeRoomDb.employeeDao().getAllEmployees();
           //     notifyDataSetChanged();

       }
   }














