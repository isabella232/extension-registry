/********************************************************************************
 * Copyright (c) 2019 TypeFox
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ********************************************************************************/

import * as React from "react";
import { Typography } from "@material-ui/core";
import { ExtensionDetailParams } from "./extension-detail";

export class ExtensionDetailOverview extends React.Component<ExtensionDetailParams> {

    render() {
        return <React.Fragment>
            <Typography variant='h3'>ExtensionDetailOverview for {this.props.extid}</Typography>
        </React.Fragment>;
    }
}