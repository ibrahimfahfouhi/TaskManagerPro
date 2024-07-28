import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AdminService} from "../../services/admin.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.scss']
})
export class UpdateTaskComponent {
  id: number = this.route.snapshot.params["id"];
  updateTaskForm!: FormGroup;
  listOfEmployees: any = [];
  listOfPriorities: any = ["LOW", "MEDIUM", "HIGH"];
  listOfTaskStatus: any = ["PENDING", "INPROGRESS", "COMPLETED", "DEFERRED", "CANCELLED"];
  constructor(private route: ActivatedRoute,
              private adminService: AdminService,
              private fb: FormBuilder,
              private snackbar: MatSnackBar,
              private router: Router) {
    this.getTaskById();
    this.getUsers();
    this.updateTaskForm = this.fb.group({
      employeeId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      dueDate: [null, [Validators.required]],
      priority: [null, [Validators.required]],
      taskStatus: [null, [Validators.required]]
    })
  }
  getTaskById() {
    this.adminService.getTaskById(this.id).subscribe((res) => {
      this.updateTaskForm.patchValue(res);
      console.log(res);
    })
  }
  getUsers() {
    this.adminService.getUsers().subscribe((res) => {
      this.listOfEmployees = res;
      console.log(res);
    })
  }
  updateTask() {
    console.log(this.updateTaskForm.value);
    this.adminService.updateTask(this.id, this.updateTaskForm.value).subscribe((res) => {
      if (res.id != null) {
        this.snackbar.open("Task updated successfully", "Close", {duration: 5000});
        this.router.navigateByUrl("/admin/dashboard");
      } else {
        this.snackbar.open("Something went wrong", "Close", {duration: 5000});
      }
    })
  }
}
