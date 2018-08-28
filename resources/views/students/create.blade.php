@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">Add New Student</div>

                    <div class="panel-body">
                      @if ($errors->count() > 0)
                      <ul>
                        @foreach($errors->all() as $error)
                        <li>{{ $error }}</li>
                        @endforeach
                      </ul>
                      @endif
                        <form action="{{ route('students.store') }}" method="post">
                            {{ csrf_field() }}
                            First name:
                            <br />
                            <input type="text" name="fname" value="{{ old('fname') }}" />
                            <br /><br />
                            Last name:
                            <br />
                            <input type="text" name="lname" value="{{ old('lname') }}" />
                            <br /><br />
                            Middle name:
                            <br />
                            <input type="text" name="mname" value="{{ old('mname') }}" />
                            <br /><br />
                            Email:
                            <br />
                            <input type="text" name="email" value="{{ old('email') }}" />
                            <br /><br />
                            Password:
                            <br />
                            <input type="text" name="password" value="{{ old('password') }}" />
                            <br /><br />
                            Contact:
                            <br />
                            <input type="text" name="contact" value="{{ old('contact') }}" />
                            <br /><br />
                            Team:
                            <br />
                            <input type="text" name="team" value="{{ old('team') }}" />
                            <br /><br />
                            Section:
                            <br />
                            <input type="text" name="section" value="{{ old('section') }}" />
                            <br /><br />
                            <input type="submit" value="Submit" class="btn btn-default" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
