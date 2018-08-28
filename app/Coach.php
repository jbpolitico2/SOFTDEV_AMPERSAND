<?php

namespace ProjectFIT;

use Illuminate\Database\Eloquent\Model;

class Coach extends Model
{
    protected $fillable = ['fname', 'lname', 'mname', 'email', 'password', 'contact', 'team', 'section'];
}
