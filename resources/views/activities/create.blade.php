@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">Add New Activity</div>

                    <div class="panel-body">
                      @if ($errors->count() > 0)
                      <ul>
                        @foreach($errors->all() as $error)
                        <li>{{ $error }}</li>
                        @endforeach
                      </ul>
                      @endif
                        <form action="{{ route('activities.store') }}" method="post">
                            {{ csrf_field() }}
                            Activity Title:
                            <br />
                            <input type="text" name="title" value="{{ old('title') }}" />
                            <br /><br />
                            Activity Description:
                            <br />
                            <input type="text" name="desc" value="{{ old('desc') }}" />
                            <br /><br />
                            <input type="submit" value="Submit" class="btn btn-default" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
