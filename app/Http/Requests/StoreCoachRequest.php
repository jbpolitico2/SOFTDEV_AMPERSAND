<?php

namespace ProjectFIT\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreCoachRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
          'fname' => 'required',
          'lname' => 'required',
          'mname' => 'required',
          'email' => 'required',
          'password' => 'required',
          'contact' => 'required'


        ];
    }
}
