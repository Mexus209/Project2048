package com.example.mygame

import kotlin.ranges.random

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

fun main() {
//    val cell1 = Grid.Cell(0, 0)
//    cell1.setValue(2)
//    val cell2 = Grid.Cell(0, 1)
//    cell2.setValue(2)
//    val cell3 = Grid.Cell(0, 2)
//    cell3.setValue(2)
//    val cell4 = Grid.Cell(1, 0)
//    cell4.setValue(2)
//    val cell5 = Grid.Cell(1, 1)
//    cell5.setValue(0)
//    val cell6 = Grid.Cell(1, 2)
//    cell6.setValue(0)
//    val cell7 = Grid.Cell(2, 0)
//    cell7.setValue(4)
//    val cell8 = Grid.Cell(2, 1)
//    cell8.setValue(4)
//    val cell9 = Grid.Cell(2, 2)
//    cell9.setValue(0)
//    val grid = listOf(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9)
//    val newGrid = Grid().moveGrid(Direction.LEFT, grid)
//    val secondGrid = Grid().moveGrid(Direction.UP, newGrid)
    val grid = Grid().createGrid()
    println(grid)
    println(grid.reversed())
}

class Grid {
    class Cell(val row: Int, val column: Int) {
        var v:Int? = null
        fun setValue(number: Int?) {
            v = number
        }

        fun getValue(): Int? {
            return v
        }

        fun getNeighbourValue(direction: Direction, grid: List<Cell>): Int? {
            val cells = mutableListOf<Cell>()
            grid.forEach {  cells.add(it) }
            when(direction) {
                Direction.UP -> if (Cell(this.row - 1, this.column) in cells)
                    return cells.find{it == Cell(this.row - 1, this.column)}?.getValue()
                Direction.DOWN -> if (Cell(this.row + 1, this.column) in cells)
                    return cells.find{it == Cell(this.row + 1, this.column)}?.getValue()
                Direction.LEFT -> if (Cell(this.row, this.column - 1,) in cells)
                    return cells.find{it == Cell(this.row, this.column - 1)}?.getValue()
                Direction.RIGHT -> if (Cell(this.row, this.column + 1) in cells)
                    return cells.find{it == Cell(this.row, this.column + 1)}?.getValue()

            }
            return null
        }
        fun getNeighbour(direction: Direction): Cell {
            return when(direction) {
                Direction.UP -> Cell(this.row - 1, this.column)
                Direction.DOWN -> Cell(this.row + 1, this.column)
                Direction.LEFT -> Cell(this.row, this.column - 1)
                Direction.RIGHT -> Cell(this.row, this.column + 1)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Cell) return false
            return this.column == other.column && this.row == other.row
        }
        override fun toString(): String {
            return "(${this.row}, ${this.column}) = ${this.getValue()}"
        }
    }


    fun createGrid(): List<Cell> {
        val grid = createEmptyGrid()
        val firstCell = Cell((0..3).random(), (0..3).random())
        var secondCell = Cell((0..3).random(), (0..3).random())
        while (firstCell == secondCell) secondCell = Cell((0..3).random(), (0..3).random())
        val firstValue =
            if ((0..9).random() > 0) 2
            else 4
        val secondValue =
            if ((0..9).random() > 0) 2
            else 4
        grid.forEach {
                when(it) {
                    firstCell -> it.setValue(firstValue)
                    secondCell -> it.setValue(secondValue)
                    else -> it.setValue(0)
                }
        }
        return grid
    }


    fun createEmptyGrid(): List<Cell> {
        val result = mutableListOf<Cell>()
        for (row in 0..3) {
            for (column in 0..3) {
                result.add(Cell(row, column))
            }
        }
        return result
    }
// Create check line(row, column) with empty cells function or try while cycle
    fun moveGrid(direction: Direction, grid: List<Cell>): List<Cell> {
        var gridResult =  listOf<Cell>()
        val gridMain = grid.toMutableList()
        val gridReversed = grid.reversed().toMutableList()
        if (direction == Direction.DOWN || direction == Direction.RIGHT) {
            gridMain.forEach {
                var cell = it
                var cellValue = it.getValue()
                var neighbour = it.getNeighbour(direction)
                var neighbourValue = it.getNeighbourValue(direction, grid)
                var sum = neighbourValue?.plus(cellValue!!)
                while (neighbourValue != null) {
                    when (neighbourValue) {
                        cellValue -> {
                            gridMain.find { it == neighbour }?.setValue(sum)
                            gridMain.find { it == cell }?.setValue(0)
                            break
                        }
                        0 -> {
                            gridMain.find { it == neighbour }?.setValue(cellValue)
                            gridMain.find { it == cell }?.setValue(0)
                            cell = neighbour
                            cellValue = neighbourValue
                            neighbour = neighbour.getNeighbour(direction)
                            neighbourValue = it.getNeighbourValue(direction, grid)
                            sum = neighbourValue?.plus(cellValue)
                        }
                        else -> break
                    }
                }
            }
            gridResult = gridMain
        }
        else {
            gridReversed.forEach {
                var cell = it
                var cellValue = it.getValue()
                var neighbour = it.getNeighbour(direction)
                var neighbourValue = it.getNeighbourValue(direction, grid)
                var sum = neighbourValue?.plus(cellValue!!)
                while (neighbourValue != null) {
                    when (neighbourValue) {
                        cellValue -> {
                            gridMain.find { it == neighbour }?.setValue(sum)
                            gridMain.find { it == cell }?.setValue(0)
                            break
                        }
                        0 -> {
                            gridMain.find { it == neighbour }?.setValue(cellValue)
                            gridMain.find { it == cell }?.setValue(0)
                            cell = neighbour
                            cellValue = neighbourValue
                            neighbour = neighbour.getNeighbour(direction)
                            neighbourValue = it.getNeighbourValue(direction, grid)
                            sum = neighbourValue?.plus(cellValue!!)
                        }
                        else -> break
                    }
                }
            }
            gridResult = gridReversed.reversed()
        }
        return gridResult
    }

}