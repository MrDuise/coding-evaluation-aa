import Position from './Position';
import Name from './Name';
import Employee from './Employee';

abstract class Organization {
  private root: Position;
  private employeeNumber: number;

  constructor() {
    this.root = this.createOrganization();
    this.employeeNumber = 0;
  }

  protected abstract createOrganization(): Position;

  printOrganization = (position: Position, prefix: string): string => {
    let str = `${prefix}+-${position}\n`;
    for (const p of position.getDirectReports()) {
      str = str.concat(this.printOrganization(p, `${prefix}  `));
    }
    return str;
  };

  // Hire the given person as an employee in the position that has that title
  // Return the newly filled position or undefined if no position has that title
  hire(person: Name, title: string): Position | undefined {
    // your code here
    const pos = this.findPosition(this.root, title);
           if (pos) {
               pos.setEmployee(new Employee(++this.employeeNumber, person));
           }
           return pos;
    return undefined
  }


    private findPosition(root: Position | undefined, title: string): Position | undefined {
        if (root === undefined) {
            return undefined;
        }

        if (root.getTitle() === title) {
            return root;
        }

        for (const report of root.getDirectReports()) {
            const result = this.findPosition(report, title);
            if (result !== undefined) {
                return result;
            }
        }

        return undefined;
    }
  toString = () => this.printOrganization(this.root, '');
}

export default Organization;
