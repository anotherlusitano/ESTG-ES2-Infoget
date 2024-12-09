<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class AdminPageTest extends TestCase
{
    use RefreshDatabase;

    /** @test */
    public function it_displays_secretaria_page()
    {
        // Faz uma requisição GET para a rota da página administrativa
        $response = $this->get('/admin');

        // Verifica se o status da resposta é 200 (com sucesso)
        $response->assertStatus(200);
                                                                        
        // Verifica se o botão da Secretaria aparece na página
        $response->assertSee('Secretaria');
    }
}
