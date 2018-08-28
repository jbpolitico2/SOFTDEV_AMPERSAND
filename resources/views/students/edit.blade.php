@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">Edit Student</div>

                    <div class="panel-body">
                      @if ($errors->count() > 0)
                      <ul>
                        @foreach($errors->all() as $error)
                        <li>{{ $error }}</li>
                        @endforeach
                      </ul>
                      @endif
                      <form action="{{ route('students.update', $student->id) }}" method="post">
                        <input type="hidden" name="_method" value="PUT">
                            {{ csrf_field() }}
                            First name:
                            <br />
                            <input type="text" name="fname" value="{{ $student->fname }}" />
                            <br /><br />
                            Last name:
                            <br />
                            <input type="text" name="lname" value="{{ $student->lname }}" />
                            <br /><br />
                            Middle name:
                            <br />
                            <input type="text" name="mname" value="{{ $student->mname }}" />
                            <br /><br />
                            Email:
                            <br />
                            <input type="text" name="email" value="{{ $student->email }}" />
                            <br /><br />
                            Password:
                            <br />
                            <input type="text" name="password" value="{{ $student->password }}" />
                            <br /><br />
                            Contact:
                            <br />
                            <input type="text" name="contact" value="{{ $student->contact }}" />
                            <br /><br />
                            Team:
                            <br />
                            <input type="text" name="team" value="{{ $student->team }}" />
                            <br /><br />
                            Section:
                            <br />
                            <input type="text" name="section" value="{{ $student->section }}" />
                            <br /><br />
                            <input type="submit" value="Submit" class="btn btn-default" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
