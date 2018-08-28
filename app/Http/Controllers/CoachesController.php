<?php

namespace ProjectFIT\Http\Controllers;
use ProjectFIT\Coach;
use ProjectFIT\Http\Requests\StoreCoachRequest;
use Illuminate\Http\Request;

class CoachesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      $coaches = Coach::all();
      return view('coaches.index', compact('coaches'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
      return view('coaches.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
     public function store(StoreCoachRequest $request)
     {
         Coach::create($request->all());
         return redirect()->route('coaches.index')->with(['message' => 'Coach added successfully']);
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
      $coach = Coach::findOrFail($id);
      return view('coaches.edit', compact('coach'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(StoreCoachRequest $request, $id)
    {
      $coach = Coach::findOrFail($id);
      $coach->update($request->all());
      return redirect()->route('coaches.index')->with(['message' => 'Coach updated successfully']);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
      $coach = Coach::findOrFail($id);
      $coach->delete();
      return redirect()->route('coaches.index')->with(['message' => 'Coach deleted successfully']);
    }
}
