@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                  <a href="{{ route('students.create') }}" class="btn btn-default">Add New Student</a>
                    <div class="panel-heading">Students</div>
                    @if (session('message'))
                            <div class="alert alert-info">{{ session('message') }}</div>
                        @endif
                    <div class="panel-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                  <th>ID Number</th>
                                  <th>First name</th>
                                  <th>Last name</th>
                                  <th>Middle name</th>
                                  <th>Email</th>
                                  <th>Password</th>
                                  <th>Contact</th>
                                  <th>Team</th>
                                  <th>Section</th>
                                  <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                @forelse($students as $student)
                                <tr>
                                  <td>{{ $student -> id }}</td>
                                  <td>{{ $student -> fname }}</td>
                                  <td>{{ $student -> lname }}</td>
                                  <td>{{ $student -> mname }}</td>
                                  <td>{{ $student -> email }}</td>
                                  <td>{{ $student -> password }}</td>
                                  <td>{{ $student -> contact }}</td>
                                  <td>{{ $student -> team }}</td>
                                  <td>{{ $student -> section }}</td>
                                    <td>
                                        <a href="{{ route('students.edit', $student->id) }}" class="btn btn-default">Edit</a>
                                        <form action="{{ route('students.destroy', $student->id) }}" method="POST"
                                              style="display: inline"
                                              onsubmit="return confirm('Are you sure?');">
                                            <input type="hidden" name="_method" value="DELETE">
                                            {{ csrf_field() }}
                                            <button class="btn btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                                @empty
                                    <tr>
                                        <td colspan="3">No entries found.</td>
                                    </tr>
                                @endforelse
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
