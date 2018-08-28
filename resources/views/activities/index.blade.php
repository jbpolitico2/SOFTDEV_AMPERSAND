@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                  <a href="{{ route('activities.create') }}" class="btn btn-default">Add New Activity</a>
                    <div class="panel-heading">Activities</div>
                    @if (session('message'))
                            <div class="alert alert-info">{{ session('message') }}</div>
                        @endif
                    <div class="panel-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                  <th>Activity ID </th>
                                  <th>Activity Title</th>
                                  <th>Activity Description</th>
                                  <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                @forelse($activities as $activity)
                                <tr>
                                  <td>{{ $activity -> id }}</td>
                                  <td>{{ $activity -> title }}</td>
                                  <td>{{ $activity -> desc }}</td>
                                    <td>
                                        <a href="{{ route('activities.edit', $activity->id) }}" class="btn btn-default">Edit</a>
                                        <form action="{{ route('activities.destroy', $activity->id) }}" method="POST"
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
