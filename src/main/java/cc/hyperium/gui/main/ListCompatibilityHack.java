/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.gui.main;

import cc.hyperium.gui.main.components.OverlayComponent;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.jetbrains.annotations.NotNull;

class ListCompatibilityHack extends AbstractList<OverlayComponent> {
    private Collection<OverlayComponent> data;

    public ListCompatibilityHack(boolean alphabetic) {
        this.data = alphabetic ? new TreeSet<>(new OverlayComparator()) : new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.data.contains(o);
    }

    @NotNull
    @Override
    public Iterator<OverlayComponent> iterator() {
        return this.data.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return this.data.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return this.data.toArray(a);
    }

    @Override
    public boolean add(OverlayComponent overlayComponent) {
        return this.data.add(overlayComponent);
    }

    @Override
    public OverlayComponent get(int index) {
        return (OverlayComponent) this.data.toArray()[index];
    }

    @Override
    public boolean remove(Object o) {
        return this.data.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return this.data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends OverlayComponent> c) {
        return this.data.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return this.data.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return this.data.retainAll(c);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    static class OverlayComparator implements Comparator<OverlayComponent> {

        @Override
        public int compare(OverlayComponent first, OverlayComponent second) {
            if (first == null && second == null) {
                return 0;
            }

            if (first == null) {
                return 1;
            }

            if (second == null) {
                return -1;
            }

            return first.getLabel().compareTo(second.getLabel());
        }
    }
}
