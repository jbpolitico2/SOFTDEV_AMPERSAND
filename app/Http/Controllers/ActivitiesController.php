<?php

namespace ProjectFIT\Http\Controllers;
use ProjectFIT\Activity;
use ProjectFIT\Http\Requests\StoreActivityRequest;
use Illuminate\Http\Request;

class ActivitiesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      $activities = Activity::all();
      return view('activities.index', compact('activities'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('activities.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(StoreActivityRequest $request)
    {
      Activity::create($request->all());
      return redirect()->route('activities.index')->with(['message' => 'Activity added successfully']);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
      $activity = Activity::findOrFail($id);
      return view('activities.edit', compact('activity'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(StoreActivityRequest $request, $id)
    {
      $activity = Activity::findOrFail($id);
      $activity->update($request->all());
      return redirect()->route('activities.index')->with(['message' => 'Activity updated successfully']);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
      $activity = Activity::findOrFail($id);
      $activity->delete();
      return redirect()->route('activities.index')->with(['message' => 'Activity deleted successfully']);
    }
}
