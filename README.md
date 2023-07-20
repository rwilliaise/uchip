# uchip

A mod built around emulating microprocessors and building computers utilizing
them. Computers can control monoliths of computer cases, or fly on drones with
power-efficient computers.

## Code Structure

In `src/main/java`, there are two folders: `core` and `content`. `core` contains
a few external-facing API classes, such as `Board` and `BoardComponent`.
`content` contains blocks, items and entities that utilize APIs from `core`.
There are a few overarching designs shown by naming schemes, which are listed
below.

### `Widget`

Some GUIs can be accessed through multiple means. For example, the assembler UI
should be accessible in the ROM item UI, as well as the circuit board UI when a
ROM is planted inside. `Widget`s are different from the Minecraft `Widget`s only
in design - Minecraft `Widget`s are small, reusable elements while some uchip
`Widget`s are full-featured GUIs. In practice, they should be similar to render
and embed in `Screen`s. See `AssemblerWidget`.

### `Component`

`Component`s are created when a valid class extending `ComponentItem` is put
into a `Board`/`BoardItem`. `Component`s are used to simulate functions of a
integrated circuit, such as RAM, ROM, or a full processor on the board.

## Processors

- [x] MOS 6502
- [ ] i386
    - Planned in the future to be the first 32-bit processor added.

