package com.example.core.pt.application.port.out

import com.example.core.pt.application.command.SavePtCommand

interface PtJpaPort {
    fun save(command: SavePtCommand)
}
