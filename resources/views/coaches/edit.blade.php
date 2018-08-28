@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">Edit Coach</div>

                    <div class="panel-body">
                      @if ($errors->count() > 0)
                      <ul>
                        @foreach($errors->all() as $error)
                        <li>{{ $error }}</li>
                        @endforeach
                      </ul>
                      @endif
                      <form action="{{ route('coaches.update', $coach->id) }}" method="post">
                        <input type="hidden" name="_method" value="PUT">
                            {{ csrf_field() }}
                            First name:
                            <br />
                            <input type="text" name="fname" value="{{ $coach->fname }}" />
                            <br /><br />
                            Last name:
                            <br />
                            <input type="text" name="lname" value="{{ $coach->lname }}" />
                            <br /><br />
                            Middle name:
                            <br />
                            <input type="text" name="mname" value="{{ $coach->mname }}" />
                            <br /><br />
                            Email:
                            <br />
                            <input type="text" name="email" value="{{ $coach->email }}" />
                            <br /><br />
                            Password:
                            <br />
                            <input type="text" name="password" value="{{ $coach->password }}" />
                            <br /><br />
                            Contact:
                            <br />
                            <input type="text" name="contact" value="{{ $coach->contact }}" />
                            <br /><br />
                            Team:
                            <br />
                            <input type="text" name="team" value="{{ $coach->team }}" />
                            <br /><br />
                            Section:
                            <br />
                            <input type="text" name="section" value="{{ $coach->section }}" />
                            <br /><br />
                            <input type="submit" value="Submit" class="btn btn-default" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
