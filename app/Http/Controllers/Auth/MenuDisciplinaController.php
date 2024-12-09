<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;

class MenuDisciplinaController extends Controller
{
    public function index()
    {
        $user = Auth::user();
        if ($user->role !== 1) {
            return redirect()->intended(route('dashboard'));
        }
        return view('admin.menudisciplina');
    }
}
